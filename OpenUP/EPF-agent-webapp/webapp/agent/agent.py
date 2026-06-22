from typing import Any
from acp import run_agent
from deepagents import CompiledSubAgent, create_deep_agent
from deepagents_acp.server import AgentServerACP
from deepagents.profiles import HarnessProfile, register_harness_profile
from langchain.agents import AgentState, create_agent
from langchain.agents.middleware.types import AgentMiddleware
from langchain.tools import BaseTool
from langchain_mcp_adapters.interceptors import MCPToolCallRequest, ToolCallInterceptor
from langchain_mcp_adapters.tools import load_mcp_tools
from mcp.types import Prompt
from langchain_openai import ChatOpenAI
from langchain_redis import RedisCache
from langgraph.types import Checkpointer
from langgraph.store.base import BaseStore
from langgraph.cache.base import BaseCache
from langgraph.checkpoint.redis import AsyncRedisSaver
from langgraph.checkpoint.redis.base import CHECKPOINT_PREFIX, CHECKPOINT_WRITE_PREFIX
from langgraph.store.redis.aio import AsyncRedisStore
from langgraph.store.redis.base import STORE_PREFIX, STORE_VECTOR_PREFIX
from langchain_mcp_adapters.client import MultiServerMCPClient
from langchain_mcp_adapters.sessions import Connection, StreamableHttpConnection
from fastapi.security import HTTPAuthorizationCredentials
from pydantic import BaseModel
from redis import asyncio
from redis.asyncio import Redis as AsyncRedis
from redis import Redis as SyncRedis
import os
import sys
from backend import AgentBackend

DEBUG = ("true" == os.getenv("DEBUG", "false"))
AGENT_NAME_PREFIX = "deep-agent-"

DEFAULT_SERVER_NAME = "gateway"

MCP_SERVER_URLS: dict[str, str] = {
    DEFAULT_SERVER_NAME: os.environ["MCP_SERVER_URL"],
    "chrome_devtools": os.environ["CHROME_DEVTOOLS_MCP_SERVER_URL"]
}
MCP_SERVERS: list[str] = [
    DEFAULT_SERVER_NAME,
    "query",
    "persistence",
    "chrome_devtools"
]

class DeepAgentState(AgentState[dict[str, Any]]):
    """"""
class DeepAgentContext(BaseModel):
    __authorization: HTTPAuthorizationCredentials
    __claims: Any
    __organization: str

    def __init__(self, authorization: HTTPAuthorizationCredentials, claims: Any, organization: str):
        self.__authorization = authorization
        self.__claims = claims
        self.__organization = organization

    def get_authorization(self) -> HTTPAuthorizationCredentials:
        return self.__authorization

    def get_claims(self) -> Any:
        return self.__claims

    def get_organization(self) -> str:
        return self.__organization
class DeepAgentMiddleware(AgentMiddleware[DeepAgentState, DeepAgentContext, dict[str, Any]]):
    """"""
class DeepAgentToolCallInterceptor(ToolCallInterceptor):
    async def __call__(
        self,
        request: MCPToolCallRequest,
        handler,
    ):
        context: DeepAgentContext = request.runtime.context
        if(context and context.get_authorization()):
            authorization = context.get_authorization()
            headers = { "Authorization": authorization.scheme + " " + authorization.credentials }
            new_request = request.override(headers=headers)
            return await handler(new_request)
        return await handler(request)

def load_servers():
    for server in MCP_SERVERS:
        if(MCP_SERVER_URLS.get(server) is None):
            MCP_SERVER_URLS[server] = os.environ["MCP_SERVER_URL_FORMAT"].format(server)

def get_connections(server_name: str, headers: dict[str, Any] | None) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    for (mcp_server_name, mcp_server_url) in MCP_SERVER_URLS.items():
        connections[mcp_server_name] = StreamableHttpConnection(
            transport = 'streamable_http',
            url=mcp_server_url,
            headers=headers
        )
    return connections

def get_client(server_name: str, authorization: HTTPAuthorizationCredentials) -> MultiServerMCPClient:
    headers: dict[str, Any] = {}
    headers["Authorization"] = authorization.scheme + " " + authorization.credentials
    connections = get_connections(server_name=server_name, headers=headers)
    tool_interceptors = [DeepAgentToolCallInterceptor()]
    client = MultiServerMCPClient(
        connections=connections,
        tool_interceptors=tool_interceptors
    )
    return client

async def load_tools(client: MultiServerMCPClient, server_name: str) -> list[BaseTool]:
    connection = client.connections[server_name]
    tool_interceptors = [DeepAgentToolCallInterceptor()]
    tools = await load_mcp_tools(session=None,connection=connection,server_name=server_name, tool_interceptors=tool_interceptors)
    return tools

async def list_prompts(client: MultiServerMCPClient, server_name: str) -> list[Prompt]:
    prompts: list[Prompt] = []
    async with client.session(server_name=server_name) as session:
        list_prompts_result = await session.list_prompts()
        prompts = list_prompts_result.prompts
    return prompts

async def get_model(organization: str) -> ChatOpenAI:
    return ChatOpenAI(model=os.environ["OPENAI_MODEL"],base_url=os.environ["OPENAI_BASE_URL"])

async def get_checkpointer(organization: str) -> Checkpointer:
    redis_url = os.environ["CHECKPOINTER_PERSISTENCE_URL_FORMAT"].format(organization)
    checkpoint_prefix = CHECKPOINT_PREFIX + "-" + organization
    checkpoint_write_prefix = CHECKPOINT_WRITE_PREFIX + "-" + organization
    redis_client = AsyncRedis(host=os.environ["REDIS_HOST"], password=os.environ["REDIS_PASSWORD"], single_connection_client=True)
    async with AsyncRedisSaver.from_conn_string(redis_url=redis_url, redis_client=redis_client, checkpoint_prefix=checkpoint_prefix, checkpoint_write_prefix=checkpoint_write_prefix) as checkpointer:
        await checkpointer.asetup()
        return checkpointer
    
async def get_store(organization: str) -> BaseStore:
    conn_string = os.environ["STORE_PERSISTENCE_URL_FORMAT"].format(os.environ["REDIS_PASSWORD"], organization)
    store_prefix = STORE_PREFIX + "-" + organization
    vector_prefix = STORE_VECTOR_PREFIX + "-" + organization
    async with AsyncRedisStore.from_conn_string(conn_string=conn_string, store_prefix=store_prefix, vector_prefix=vector_prefix) as store:
        await store.setup()
        return store
    
async def get_cache(organization: str) -> BaseCache:
    redis_url = os.environ["CACHE_PERSISTENCE_URL_FORMAT"].format(organization)
    prefix = "redis-" + organization
    redis_client = SyncRedis(host=os.environ["REDIS_HOST"], password=os.environ["REDIS_PASSWORD"], single_connection_client=True)
    redis_cache = RedisCache(redis_url=redis_url, prefix=prefix, redis_client=redis_client)
    return redis_cache

async def create_sub_agent(
        organization: str,
        authorization: HTTPAuthorizationCredentials,
        prompt: Prompt,
        model: ChatOpenAI,
        checkpointer: Checkpointer,
        store: BaseStore,
        cache: BaseCache) -> CompiledSubAgent:
    server_name = prompt.name
    client = get_client(server_name=server_name, authorization=authorization)
    tools = await load_tools(client=client, server_name=server_name)
    system_prompt = await client.get_prompt(server_name=server_name, prompt_name=prompt.name)
    name = prompt.name + "-" + organization
    agent = create_agent(
        model=model,
        tools=tools,
        system_prompt=system_prompt[0].content,
        middleware=[DeepAgentMiddleware()],
        response_format=dict[str, Any],
        state_schema=DeepAgentState,
        context_schema=DeepAgentContext,
        checkpointer=checkpointer,
        store=store,
        debug=DEBUG,
        name=name,
        cache=cache
    )
    sub_agent = CompiledSubAgent(name = prompt.name, description=prompt.description, agent=agent, runnable=agent)
    return sub_agent

async def create_supervisor_agent(organization: str, authorization: HTTPAuthorizationCredentials):
    model = await get_model(organization=organization)
    server_name = DEFAULT_SERVER_NAME
    client = get_client(server_name=server_name, authorization=authorization)
    tools = await load_tools(client=client, server_name=server_name)
    prompts = await list_prompts(client=client, server_name=server_name)
    system_prompt: str | None = None
    sub_agent_prompts: list[Prompt] = []
    for agent_prompt in prompts:
        if (agent_prompt.name == DEFAULT_SERVER_NAME):
            prompt = await client.get_prompt(server_name=server_name, prompt_name=agent_prompt.name)
            system_prompt = prompt[0].content
        else:
            sub_agent_prompts.append(agent_prompt)
    subagents: list[CompiledSubAgent] = []
    checkpointer = await get_checkpointer(organization=organization)
    store = await get_store(organization=organization)
    cache = await get_cache(organization=organization)
    for sub_agent_prompt in sub_agent_prompts:
        sub_agent = await create_sub_agent(
            organization=organization, 
            authorization=authorization, 
            prompt=sub_agent_prompt, 
            model=model, 
            checkpointer=checkpointer,
            store=store, 
            cache=cache)
        subagents.append(sub_agent)
    backend = AgentBackend()
    name = AGENT_NAME_PREFIX + organization
    agent = create_deep_agent(
        model=model,
        tools=tools,
        system_prompt=system_prompt,
        middleware = [DeepAgentMiddleware()],
        subagents=subagents,
        backend=backend,
        response_format=dict[str, Any],
        state_schema=DeepAgentState,
        context_schema=DeepAgentContext,
        checkpointer=checkpointer,
        store=store,
        debug=DEBUG,
        name=name,
        cache=cache
    )
    return agent

async def get_server(organization: str, authorization: HTTPAuthorizationCredentials) -> AgentServerACP:
    agent = await create_supervisor_agent(organization=organization, authorization=authorization)
    server = AgentServerACP(agent)
    return server

async def run(organization: str, authorization: HTTPAuthorizationCredentials) -> None:
    server = await get_server(organization=organization, authorization=authorization)
    await run_agent(server)

if __name__ == "__main__":
    profile = HarnessProfile(excluded_tools=frozenset({""}))
    register_harness_profile(key="openai", profile=profile)
    organization = sys.argv[1]
    authorization = HTTPAuthorizationCredentials()
    asyncio.run(run(organization=organization, authorization=authorization))