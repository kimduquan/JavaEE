from typing import Any, TypedDict
from fastapi import FastAPI, Request
from fastapi.responses import StreamingResponse
from langchain_openai import ChatOpenAI
from langchain.agents import AgentState
from langgraph.graph.state import CompiledStateGraph, StateGraph, START, END
from langchain.agents.factory import create_agent
import os
from langchain_core.tools.base import BaseTool
from langchain_mcp_adapters.client import MultiServerMCPClient
from langchain_mcp_adapters.sessions import Connection, StreamableHttpConnection
from langchain_mcp_adapters.tools import load_mcp_tools
from mcp.types import Prompt
from langgraph_supervisor.handoff import create_handoff_tool
from langgraph_supervisor.supervisor import create_supervisor
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

class EPFAgentState(AgentState):
    """"""

class AgentInput(TypedDict):
    """"""

class AgentOutput(TypedDict):
    """"""

class AgentContext():
    authorization: str
    model: ChatOpenAI
    checkpointer: Checkpointer
    store: BaseStore
    cache: BaseCache

class EPFAgentMiddleware(AgentMiddleware[EPFAgentState, AgentContext]):
    """"""

mcp_server_urls: dict[str, str] = {
    "main": os.environ["MCP_SERVER_URL"],
}
mcp_servers: list[str] = [
    "main"
]

SUPERVISOR_AGENT_NAME = "supervisor"

def get_model() -> ChatOpenAI:
    return ChatOpenAI(model=os.environ["OPENAI_MODEL"],base_url=os.environ["OPENAI_BASE_URL"])

def load_servers():
    for server in mcp_servers:
        if(mcp_server_urls.get(server) == None):
            mcp_server_urls[server] = os.environ["MCP_SERVER_URL_FORMAT"].format(server)

def get_connections(server_name: str, context: AgentContext) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    headers: dict[str, Any] = {}
    headers["Authorization"] = context.authorization
    print("Authorization=" + context.authorization)
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

async def create_main_agent(main_server_name: str, main_prompt_name: str, main_agent_name: str, context: AgentContext) -> CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput]:
    main_client = get_client(server_name=main_server_name,context=context)
    tools: list[BaseTool] = []
    prompts = await list_prompts(client=main_client,server_name=main_server_name)
    for prompt in prompts:
        if(prompt.name != main_prompt_name):
            agent_name = prompt.name
            agent_server_name = prompt.name
            agent_prompt = await main_client.get_prompt(server_name=agent_server_name,prompt_name=prompt.name)
            agent_client = get_client(server_name=agent_server_name,context=context)
            agent_tools = await load_tools(client=agent_client,server_name=agent_server_name)
            agent = create_agent(
                model=context.model,
                tools=agent_tools,
                system_prompt=agent_prompt[0].content,
                middleware=EPFAgentMiddleware(),
                state_schema=EPFAgentState,
                context_schema=AgentContext,
                checkpointer=context.checkpointer,
                store=context.store,
                name=agent_name,
                cache=context.cache)
            tool = agent.as_tool(name=agent_name,description=prompt.description)
            tools.append(tool)

    main_prompt = await main_client.get_prompt(server_name=main_server_name,prompt_name=main_prompt_name)
    main_system_prompt: str = main_prompt[0].content
    return create_agent(
        model=context.model,
        tools=tools,
        system_prompt=main_system_prompt,
        middleware=EPFAgentMiddleware(),
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        checkpointer=context.checkpointer,
        store=context.store,
        name=main_agent_name,
        cache=context.cache)

async def create_supervisor_agent(context: AgentContext) -> CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput]:
    handoff_tools: list[BaseTool] = []
    handoff_agents: list[CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput]] = []
    for mcp_server in mcp_servers:
        handoff_agent = await create_main_agent(
            main_server_name=mcp_server,
            main_prompt_name=mcp_server,
            main_agent_name=mcp_server,
            context=context
        )
        handoff_tool = create_handoff_tool(agent_name=handoff_agent.name)
        handoff_agents.append(handoff_agent)
        handoff_tools.append(handoff_tool)
    supervisor_agent = create_supervisor(
        handoff_agents,
        model=context.model,
        tools=handoff_tools,
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        add_handoff_messages=False,
        add_handoff_back_messages=False
    )
    return supervisor_agent.compile(checkpointer=context.checkpointer,store=context.store,cache=context.cache)

async def supervisor_node(state: EPFAgentState, config: RunnableConfig, runtime: Runtime[AgentContext]) -> dict[str, Any] | Any:
    context = AgentContext()
    context.authorization = config["configurable"]["authorization"]
    context.model = get_model()
    context.cache = InMemoryCache()
    context.checkpointer = InMemorySaver()
    context.store = InMemoryStore()
    supervisor_agent = await create_supervisor_agent(context=context)
    output = await supervisor_agent.ainvoke(input=AgentInput(state),config=config,context=context)
    return output

def supervisor_edge(state: EPFAgentState, config: RunnableConfig, runtime: Runtime[AgentContext]) -> str:
    if "authorization" in config["configurable"].keys():
        return "OK"
    return "N/A"

def add_agent_endpoint(app: FastAPI, graph: CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput], path: str = "/"):

    @app.post(path)
    async def agent_endpoint(input_data: RunAgentInput, request: Request):
        authorization: str = request.headers.get("authorization")
        config = RunnableConfig(configurable={"authorization": authorization},run_id=UUID(input_data.run_id))
        agent = LangGraphAgent(name="epf-agent", graph=graph, config=config)

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

    @app.get(f"{path}/health")
    def health():
        return {
            "status": "ok"
        }

load_servers()
builder = StateGraph(
    state_schema=EPFAgentState,
    context_schema=AgentContext,
    input_schema=AgentInput,
    output_schema=AgentOutput)
builder.add_node(SUPERVISOR_AGENT_NAME, supervisor_node)
builder.add_conditional_edges(START, supervisor_edge, {"OK":SUPERVISOR_AGENT_NAME,"N/A": END})
builder.set_finish_point(SUPERVISOR_AGENT_NAME)
supervisor_graph = builder.compile(checkpointer=InMemorySaver(),cache=InMemoryCache(),store=InMemoryStore())

app = FastAPI()
add_agent_endpoint(app=app,graph=supervisor_graph)