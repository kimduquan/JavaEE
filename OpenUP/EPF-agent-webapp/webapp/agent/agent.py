import asyncio
from typing import Annotated, Any, TypedDict
from fastapi import Depends, FastAPI, Request
from fastapi.responses import StreamingResponse
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
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
from langchain.agents.middleware.types import AgentMiddleware
from ag_ui_langgraph import LangGraphAgent
from langgraph.checkpoint.memory import InMemorySaver
from langgraph.store.memory import InMemoryStore
from langgraph.cache.memory import InMemoryCache
from ag_ui.core.types import RunAgentInput
from ag_ui.encoder import EventEncoder
from langgraph.types import Checkpointer
from langgraph.store.base import BaseStore
from langgraph.cache.base import BaseCache
from uuid import UUID
import jwt
from jwt import PyJWKClient

class EPFAgentState(AgentState):
    """"""

class AgentInput(TypedDict):
    """"""

class AgentOutput(TypedDict):
    """"""

class AgentContext():
    authorization: HTTPAuthorizationCredentials
    claims: Any
    model: ChatOpenAI
    checkpointer: Checkpointer
    store: BaseStore
    cache: BaseCache

class EPFAgentMiddleware(AgentMiddleware[EPFAgentState, AgentContext]):
    """"""


DEFAULT_SERVER_NAME = "epf-mcp-server"

mcp_server_urls: dict[str, str] = {
    DEFAULT_SERVER_NAME: os.environ["MCP_SERVER_URL"],
}
mcp_servers: list[str] = [
    DEFAULT_SERVER_NAME
]

EPF_AGENT_NAME = "epf-agent"
SUPERVISOR_AGENT_NAME = "supervisor"
SUPERVISOR_AGENT_GRAPH: CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput] = None
SUPERVISOR_AGENT_CONTEXT: AgentContext = AgentContext()
SUPERVISOR_AGENT_CONTEXT.cache = InMemoryCache()
SUPERVISOR_AGENT_CONTEXT.checkpointer = InMemorySaver()
SUPERVISOR_AGENT_CONTEXT.store = InMemoryStore()

security = HTTPBearer()
jwk_client = PyJWKClient(uri=os.environ["JWT_KEY_URL"])

def get_model() -> ChatOpenAI:
    return ChatOpenAI(model=os.environ["OPENAI_MODEL"],base_url=os.environ["OPENAI_BASE_URL"])

def load_servers():
    for server in mcp_servers:
        if(mcp_server_urls.get(server) == None):
            mcp_server_urls[server] = os.environ["MCP_SERVER_URL_FORMAT"].format(server)

def get_connections(server_name: str, context: AgentContext) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    headers: dict[str, Any] = {}
    headers["Authorization"] = context.authorization.scheme + " " + context.authorization.credentials
    for (mcp_server_name, mcp_server_url) in mcp_server_urls.items():
        if(mcp_server_name == server_name):
            connections[mcp_server_name] = StreamableHttpConnection(
            transport = 'streamable_http',
            url=mcp_server_url,
            headers=headers
        )
    return connections

def get_client(server_name: str, context: AgentContext) -> MultiServerMCPClient:
    connections = get_connections(server_name=server_name,context=context)
    client = MultiServerMCPClient(
        connections=connections
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

async def create_sub_agent(server_name: str, agent_name: str, prompt: Prompt, context: AgentContext) -> CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput]:
    client = get_client(server_name=server_name,context=context)
    tools: list[BaseTool] = await load_tools(client=client,server_name=server_name)
    system_prompt: str = prompt[0].content
    return create_agent(
        model=context.model,
        tools=tools,
        system_prompt=system_prompt,
        middleware=EPFAgentMiddleware(),
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        checkpointer=context.checkpointer,
        store=context.store,
        name=agent_name,
        cache=context.cache)

def as_tool(name: str, prompt: Prompt, agent: CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput]) -> BaseTool:
    arg_types: dict[str, type] = {}
    for argument in prompt.arguments:
        if(argument.required == True):
            arg_types[argument.name] = str
        else:
            arg_types[argument.name] = str | None
    tool = agent.as_tool(name=name, description=prompt.description, arg_types=arg_types)
    return tool

async def create_supervisor_agent(supervisor_agent_name: str, server_name: str, context: AgentContext) -> CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput]:
    client = get_client(server_name=server_name, context=context)
    prompts = await list_prompts(client=client, server_name=server_name)
    system_prompt: str | None = None
    sub_agent_prompts: list[Prompt] = []
    for agent_prompt in prompts:
        if (agent_prompt.name == supervisor_agent_name):
            prompt = await client.get_prompt(server_name=server_name, prompt_name=agent_prompt.name)
            system_prompt = prompt[0].content
        else:
            sub_agent_prompts.append(agent_prompt)

    sub_agents: list[CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput]] = []
    tools: list[BaseTool] = []
    for sub_agent_prompt in sub_agent_prompts:
        sub_agent_name = agent_prompt.name
        sub_agent_server_name = sub_agent_name
        sub_agent = create_sub_agent(server_name=sub_agent_server_name, agent_name=sub_agent_name, prompt=sub_agent_prompt, context=context)
        sub_agents.append(sub_agent)
        tool = as_tool(name=sub_agent_name, prompt=agent_prompt, agent=sub_agent)
        tools.append(tool)

    return create_agent(
        model=context.model,
        tools=tools,
        system_prompt=system_prompt,
        middleware=EPFAgentMiddleware(),
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        checkpointer=context.checkpointer,
        store=context.store,
        name=supervisor_agent_name,
        cache=context.cache)

async def supervisor_node(state: EPFAgentState, config: RunnableConfig, runtime: Runtime[AgentContext]) -> dict[str, Any] | Any:
    context = AgentContext()
    context.authorization = config["configurable"]["authorization"]
    context.claims = config["configurable"]["claims"]
    context.model = get_model()
    context.cache = InMemoryCache()
    context.checkpointer = InMemorySaver()
    context.store = InMemoryStore()
    output = await SUPERVISOR_AGENT_GRAPH.ainvoke(input=AgentInput(state),config=config,context=context)
    return output

def add_agent_endpoint(app: FastAPI, name: str, graph: CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput], path: str = "/"):

    @app.post(path)
    async def agent_endpoint(input_data: RunAgentInput, request: Request, credentials: Annotated[HTTPAuthorizationCredentials, Depends(security)]):
        key = jwk_client.get_signing_key_from_jwt(token=credentials.credentials)
        claims = jwt.decode(jwt=credentials.credentials, key=key)
        config = RunnableConfig(configurable={"authorization": credentials, "claims": claims}, run_id=UUID(input_data.run_id))
        agent = LangGraphAgent(name=name, graph=graph, config=config)

        accept_header = request.headers.get("accept")

        encoder = EventEncoder(accept=accept_header)

        async def event_generator():
            async for event in agent.run(input_data):
                yield encoder.encode(event)

        return StreamingResponse(
            event_generator(),
            media_type=encoder.get_content_type()
        )

    @app.get(f"{path}/health")
    def health():
        return {
            "status": "ok"
        }

load_servers()

SUPERVISOR_AGENT_GRAPH = asyncio.run(create_supervisor_agent(supervisor_agent_name=SUPERVISOR_AGENT_NAME,server_name=DEFAULT_SERVER_NAME,context=SUPERVISOR_AGENT_CONTEXT))
builder = StateGraph(
    state_schema=EPFAgentState,
    context_schema=AgentContext,
    input_schema=AgentInput,
    output_schema=AgentOutput)
builder.add_node(SUPERVISOR_AGENT_NAME, supervisor_node)
builder.set_entry_point(SUPERVISOR_AGENT_NAME)
builder.set_finish_point(SUPERVISOR_AGENT_NAME)
supervisor_graph = builder.compile(checkpointer=SUPERVISOR_AGENT_CONTEXT.checkpointer,cache=SUPERVISOR_AGENT_CONTEXT.cache,store=SUPERVISOR_AGENT_CONTEXT.store)

app = FastAPI()
add_agent_endpoint(app=app, name=EPF_AGENT_NAME, graph=supervisor_graph)