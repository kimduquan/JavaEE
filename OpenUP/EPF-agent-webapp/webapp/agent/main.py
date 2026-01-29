from datetime import datetime, timezone
from typing import Annotated, Any
from fastapi import Depends, FastAPI, HTTPException, Request
from fastapi.responses import StreamingResponse
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
from redis import Redis
import uvicorn
from langchain_openai import ChatOpenAI
from langchain.agents import AgentState
from langgraph.graph.state import CompiledStateGraph, StateGraph
from langchain.agents.factory import create_agent
import os
from langchain_core.tools.base import BaseTool
from langchain_mcp_adapters.client import MultiServerMCPClient
from langchain_mcp_adapters.sessions import Connection, StreamableHttpConnection
from langchain_mcp_adapters.tools import load_mcp_tools
from mcp.types import Prompt
from langgraph.config import RunnableConfig
from langgraph.runtime import Runtime
from langchain.agents.middleware import AgentMiddleware, SummarizationMiddleware, ModelCallLimitMiddleware, HumanInTheLoopMiddleware, ToolCallLimitMiddleware, PIIMiddleware, ToolRetryMiddleware, ModelRetryMiddleware, ContextEditingMiddleware, ClearToolUsesEdit
from langchain.agents.middleware.human_in_the_loop import InterruptOnConfig
from ag_ui_langgraph import LangGraphAgent
from ag_ui.core.types import RunAgentInput
from ag_ui.encoder import EventEncoder
from langgraph.types import Checkpointer
from langgraph.store.base import BaseStore
from langgraph.cache.base import BaseCache
from uuid import UUID
import jwt
from jwt import PyJWKClient
from copilotkit import CopilotKitMiddleware, CopilotKitState
from copilotkit.langgraph import copilotkit_messages_to_langchain, langchain_messages_to_copilotkit, copilotkit_customize_config
from langchain_mcp_adapters.interceptors import MCPToolCallRequest, ToolCallInterceptor
from aiocache import cached
from langgraph.checkpoint.redis import RedisSaver
from langgraph.checkpoint.redis.base import CHECKPOINT_PREFIX, CHECKPOINT_BLOB_PREFIX, CHECKPOINT_WRITE_PREFIX
from langgraph.store.redis import RedisStore
from langgraph.store.redis.base import STORE_PREFIX, STORE_VECTOR_PREFIX
from langchain_redis import RedisCache
from opentelemetry.instrumentation.fastapi import FastAPIInstrumentor
import logging
from jwt.exceptions import PyJWTError
from starlette.status import HTTP_403_FORBIDDEN

class UIAgentState(CopilotKitState):
    """"""

class EPFAgentState(AgentState):
    """"""

class AgentContext():
    authorization: HTTPAuthorizationCredentials
    claims: Any
    organization: str

class EPFAgentMiddleware(AgentMiddleware[EPFAgentState, AgentContext]):
    """"""

class EPFToolCallInterceptor(ToolCallInterceptor):
    """"""

    async def __call__(
        self,
        request: MCPToolCallRequest,
        handler,
    ):
        context: AgentContext = request.runtime.context
        if(context.authorization):
            headers = { "Authorization": context.authorization.scheme + " " + context.authorization.credentials }
            new_request = request.override(headers=headers)
            return await handler(new_request)
        return await handler(request)


DEFAULT_SERVER_NAME = "epf-mcp-server"

mcp_server_urls: dict[str, str] = {
    DEFAULT_SERVER_NAME: os.environ["MCP_SERVER_URL"],
}
mcp_servers: list[str] = [
    DEFAULT_SERVER_NAME,
    "query",
    "persistence"
]

DEBUG = ("true" == os.getenv("DEBUG", "false"))

MODEL_CALL_THREAD_LIMIT = int(os.environ["MODEL_CALL_THREAD_LIMIT"])
MODEL_CALL_RUN_LIMIT = int(os.environ["MODEL_CALL_RUN_LIMIT"])
MODEL_CALL_LIMIT = ModelCallLimitMiddleware(thread_limit=MODEL_CALL_THREAD_LIMIT, run_limit=MODEL_CALL_RUN_LIMIT, exit_behavior="error")
TOOL_CALL_THREAD_LIMIT = int(os.environ["TOOL_CALL_THREAD_LIMIT"])
TOOL_CALL_RUN_LIMIT = int(os.environ["TOOL_CALL_RUN_LIMIT"])
TOOL_CALL_LIMIT = ToolCallLimitMiddleware(thread_limit=TOOL_CALL_THREAD_LIMIT, run_limit=TOOL_CALL_RUN_LIMIT, exit_behavior="error")
PII = PIIMiddleware(pii_type="email")
TOOL_RETRY = ToolRetryMiddleware()
MODEL_RETRY = ModelRetryMiddleware()
CONTEXT_EDITING = ContextEditingMiddleware(edits=[ClearToolUsesEdit()])
HUMAN_IN_THE_LOOP = HumanInTheLoopMiddleware(interrupt_on={"persistence": InterruptOnConfig(allowed_decisions=["approve","edit","reject"])})

EPF_AGENT_NAME = "epf-agent"
SUPERVISOR_AGENT_NAME = "supervisor_agent"
SUPERVISOR_NODE_NAME = "supervisor"

security = HTTPBearer()
jwk_client = PyJWKClient(uri=os.environ["JWT_KEY_URL"])
logger = logging.getLogger(__name__)

def load_servers():
    for server in mcp_servers:
        if(mcp_server_urls.get(server) == None):
            mcp_server_urls[server] = os.environ["MCP_SERVER_URL_FORMAT"].format(server)

def get_connections(server_name: str) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    for (mcp_server_name, mcp_server_url) in mcp_server_urls.items():
        if(mcp_server_name == server_name):
            connections[mcp_server_name] = StreamableHttpConnection(
            transport = 'streamable_http',
            url=mcp_server_url
        )
    return connections

def get_client(server_name: str) -> MultiServerMCPClient:
    connections = get_connections(server_name=server_name)
    tool_interceptors = [EPFToolCallInterceptor()]
    client = MultiServerMCPClient(
        connections=connections,
        tool_interceptors=tool_interceptors
    )
    return client

async def load_tools(client: MultiServerMCPClient, server_name: str) -> list[BaseTool]:
    connection = client.connections[server_name]
    tools = await load_mcp_tools(session=None,connection=connection,server_name=server_name)
    return tools

async def list_prompts(client: MultiServerMCPClient, server_name: str) -> list[Prompt]:
    prompts: list[Prompt] = []
    async with client.session(server_name=server_name) as session:
        list_prompts_result = await session.list_prompts()
        prompts = list_prompts_result.prompts
    return prompts

def as_tool(name: str, prompt: Prompt, agent: CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState]) -> BaseTool:
    arg_types: dict[str, type] = {}
    for argument in prompt.arguments:
        if(argument.required == True):
            arg_types[argument.name] = str
        else:
            arg_types[argument.name] = str | None
    tool = agent.as_tool(name=name, description=prompt.description, arg_types=arg_types)
    return tool

async def create_sub_agent(organization: str, server_name: str, agent_name: str, prompt: Prompt) -> CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState]:
    client = get_client(server_name=server_name)
    tools: list[BaseTool] = await load_tools(client=client,server_name=server_name)
    system_prompt: str = prompt[0].content
    model = get_model(organization=organization)
    checkpointer = get_checkpointer(organization=organization)
    store = get_store(organization=organization)
    cache = get_cache(organization=organization)
    return create_agent(
        model=model,
        tools=tools,
        system_prompt=system_prompt,
        middleware=[
            EPFAgentMiddleware()
            ],
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        checkpointer=checkpointer,
        store=store,
        debug=DEBUG,
        name=agent_name,
        cache=cache)

@cached
def get_model(organization: str) -> ChatOpenAI:
    return ChatOpenAI(model=os.environ["OPENAI_MODEL"],base_url=os.environ["OPENAI_BASE_URL"])

@cached
def get_checkpointer(organization: str) -> Checkpointer:
    redis_url = os.environ["CHECKPOINTER_PERSISTENCE_URL_FORMAT"].format(organization)
    checkpoint_prefix = CHECKPOINT_PREFIX + "-" + organization
    checkpoint_blob_prefix = CHECKPOINT_BLOB_PREFIX + "-" + organization
    checkpoint_write_prefix = CHECKPOINT_WRITE_PREFIX + "-" + organization
    redis_client = Redis(host=os.environ["REDIS_HOST"], password=os.environ["REDIS_PASSWORD"])
    with RedisSaver.from_conn_string(redis_url=redis_url, redis_client=redis_client, checkpoint_prefix=checkpoint_prefix, checkpoint_blob_prefix=checkpoint_blob_prefix, checkpoint_write_prefix=checkpoint_write_prefix) as checkpointer:
        checkpointer.setup()
        return checkpointer

@cached
def get_store(organization: str) -> BaseStore:
    conn_string = os.environ["STORE_PERSISTENCE_URL_FORMAT"].format(organization)
    store_prefix = STORE_PREFIX + "-" + organization
    vector_prefix = STORE_VECTOR_PREFIX + "-" + organization
    with RedisStore.from_conn_string(conn_string=conn_string, store_prefix=store_prefix, vector_prefix=vector_prefix) as store:
        store.setup()
        return store

@cached
def get_cache(organization: str) -> BaseCache:
    redis_url = os.environ["CACHE_PERSISTENCE_URL_FORMAT"].format(organization)
    prefix = "redis-" + organization
    redis_client = Redis(host=os.environ["REDIS_HOST"], password=os.environ["REDIS_PASSWORD"])
    redis_cache = RedisCache(redis_url=redis_url, prefix=prefix, redis_client=redis_client)
    return redis_cache

@cached
async def create_supervisor_agent(organization: str) -> CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState]:
    supervisor_agent_name = SUPERVISOR_AGENT_NAME + "-" + organization
    server_name = DEFAULT_SERVER_NAME
    client = get_client(server_name=server_name)
    prompts = await list_prompts(client=client, server_name=server_name)
    system_prompt: str | None = None
    sub_agent_prompts: list[Prompt] = []
    for agent_prompt in prompts:
        if (agent_prompt.name == supervisor_agent_name):
            prompt = await client.get_prompt(server_name=server_name, prompt_name=agent_prompt.name)
            system_prompt = prompt[0].content
        else:
            sub_agent_prompts.append(agent_prompt)

    sub_agents: list[CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState]] = []
    tools: list[BaseTool] = []
    for sub_agent_prompt in sub_agent_prompts:
        sub_agent_name = agent_prompt.name
        sub_agent_server_name = sub_agent_name
        sub_agent = create_sub_agent(server_name=sub_agent_server_name, agent_name=sub_agent_name, prompt=sub_agent_prompt)
        sub_agents.append(sub_agent)
        tool = as_tool(name=sub_agent_name, prompt=agent_prompt, agent=sub_agent)
        tools.append(tool)
    model = get_model(organization=organization)
    checkpointer = get_checkpointer(organization=organization)
    store = get_store(organization=organization)
    cache = get_cache(organization=organization)
    return create_agent(
        model=model,
        tools=tools,
        system_prompt=system_prompt,
        middleware=[
            CopilotKitMiddleware(),
            MODEL_CALL_LIMIT,
            TOOL_CALL_LIMIT,
            PII,
            TOOL_RETRY,
            MODEL_RETRY,
            CONTEXT_EDITING,
            HUMAN_IN_THE_LOOP,
            SummarizationMiddleware(model=model),
            EPFAgentMiddleware()
            ],
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        checkpointer=checkpointer,
        store=store,
        debug=DEBUG,
        name=supervisor_agent_name,
        cache=cache)

@cached
async def create_supervisor(organization: str) -> CompiledStateGraph[UIAgentState, AgentContext, UIAgentState, UIAgentState]:
    builder = StateGraph(
        state_schema=UIAgentState,
        context_schema=AgentContext,
        input_schema=UIAgentState,
        output_schema=UIAgentState
    )
    supervisor_node_name = SUPERVISOR_NODE_NAME + "-" + organization
    builder.add_node(supervisor_node_name, supervisor_node)
    builder.set_entry_point(supervisor_node_name)
    builder.set_finish_point(supervisor_node_name)
    checkpointer = get_checkpointer(organization=organization)
    store = get_store(organization=organization)
    cache = get_cache(organization=organization)
    return builder.compile(checkpointer=checkpointer, cache=cache, store=store, debug=DEBUG, name=organization)

async def supervisor_node(state: UIAgentState, config: RunnableConfig, runtime: Runtime[AgentContext]) -> dict[str, Any] | Any:
    context = AgentContext()
    context.authorization = config["configurable"]["authorization"]
    context.claims = config["configurable"]["claims"]
    organization: str = config["configurable"]["organization"]
    context.organization = organization
    messages = copilotkit_messages_to_langchain(state["messages"])
    agent_state = EPFAgentState(messages=messages)
    supervisor_agent: CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState] = await create_supervisor_agent(organization=organization)
    output = await supervisor_agent.ainvoke(input=agent_state, config=config, context=context)
    output["messages"] = langchain_messages_to_copilotkit(output["messages"])
    return output

def add_agent_endpoint(app: FastAPI, name: str, path: str = "/"):

    @app.post(path)
    async def agent_endpoint(input_data: RunAgentInput, request: Request, credentials: Annotated[HTTPAuthorizationCredentials, Depends(security)]):

        claims: Any = None
        try:
            key = jwk_client.get_signing_key_from_jwt(token=credentials.credentials)
            claims = jwt.decode(jwt=credentials.credentials, key=key, issuer=os.environ["JWT_ISSUER"])
        except PyJWTError as ex:
            logger.error("[%f]jwt:%s", datetime.now(tz=timezone.utc).timestamp(), credentials.credentials)
            raise HTTPException(status_code=HTTP_403_FORBIDDEN, detail=ex.args)
        organization_claim: dict[str, Any] = claims["organization"]
        organization_name: str = list(organization_claim.keys())[0]
        organization: str = organization_claim.get(organization_name)["id"]
        config = RunnableConfig(configurable={"authorization": credentials, "claims": claims, "organization": organization}, run_id=UUID(input_data.run_id))
        config = copilotkit_customize_config(base_config=config)
        supervisor_graph = create_supervisor(organization=organization)
        agent = LangGraphAgent(name=name, graph=supervisor_graph, config=config)

        # Get the accept header from the request
        accept_header = request.headers.get("accept")

        # Create an event encoder to properly format SSE events
        encoder = EventEncoder(accept=accept_header)

        async def event_generator():
            async for event in agent.run(input_data):
                yield encoder.encode(event)

        return StreamingResponse(
            event_generator(),
            media_type=encoder.get_content_type()
        )

    @app.get("/health")
    def health():
        """Health check."""
        return {
            "status": "ok",
            "agent": {
                "name": name,
            }
        }

load_servers()
app = FastAPI()
add_agent_endpoint(app=app, name=EPF_AGENT_NAME)
FastAPIInstrumentor.instrument_app(app=app, excluded_urls="/health")

if __name__ == "__main__":
    uvicorn.run(app=app, host="0.0.0.0", port=8123)