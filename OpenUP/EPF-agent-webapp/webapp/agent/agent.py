from typing import Any, TypedDict
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

class EPFAgentState(AgentState):
    """"""

class AgentInput(TypedDict):
    """"""

class AgentOutput(TypedDict):
    """"""

class AgentContext:
    authorization: str = None

class EPFAgentMiddleware(AgentMiddleware[EPFAgentState, AgentContext]):
    """"""

def get_model() -> ChatOpenAI:
    return ChatOpenAI(model=os.environ["OPENAI_MODEL"],base_url=os.environ["OPENAI_BASE_URL"])

mcp_server_urls: dict[str, str] = {
    "main": os.environ["MCP_SERVER_URL"],
}
mcp_servers: list[str] = [
    "main"
]

def load_servers():
    for server in mcp_servers:
        if(mcp_server_urls.get(server) == None):
            mcp_server_urls[server] = os.environ["MCP_SERVER_URL_FORMAT"].format(server)

def get_connections(server_name: str, context: AgentContext) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    for (mcp_server_name, mcp_server_url) in mcp_server_urls.items():
        if(mcp_server_name == server_name):
            connections[mcp_server_name] = StreamableHttpConnection(
            transport = 'streamable_http',
            url=mcp_server_url,
            headers={"Authorization": context.authorization}
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
    model = get_model()
    main_client = get_client(server_name=main_server_name,context=context)
    tools: list[BaseTool] = []
    prompts = await list_prompts(client=main_client,server_name=main_server_name)
    for prompt in prompts:
        if(prompt.name != main_prompt_name):
            agent_name = prompt.name
            agent_server_name = prompt.name
            agent_prompt = await main_client.get_prompt(server_name=agent_server_name,prompt_name=prompt.name)
            agent_client = get_client(server_name=agent_server_name)
            agent_tools = await load_tools(client=agent_client,server_name=agent_server_name)
            agent = create_agent(
                model=model,
                tools=agent_tools,
                system_prompt=agent_prompt[0].content,
                middleware=EPFAgentMiddleware(),
                state_schema=EPFAgentState,
                context_schema=AgentContext,
                name=agent_name)
            tool = agent.as_tool(name=agent_name,description=prompt.description)
            tools.append(tool)

    main_prompt = await main_client.get_prompt(server_name=main_server_name,prompt_name=main_prompt_name)
    main_system_prompt: str = main_prompt[0].content
    return create_agent(
        model=model,
        tools=tools,
        system_prompt=main_system_prompt,
        middleware=EPFAgentMiddleware(),
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        name=main_agent_name)

async def create_supervisor_agent(context: AgentContext) -> CompiledStateGraph[EPFAgentState, AgentContext, AgentInput, AgentOutput]:
    model = get_model()
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
        model=model,
        tools=handoff_tools,
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        add_handoff_messages=False,
        add_handoff_back_messages=False
    )
    return supervisor_agent.compile()

def authenticate(state: EPFAgentState, config: RunnableConfig, runtime: Runtime[AgentContext]) -> str:
    print("state items:")
    for (name, value) in state.items():
        print(name)
    print("config items:")
    for (name, value) in config.items():
        print(name)
    print("configurable items:")
    for (name, value) in config.configurable.items():
        print(name)
    auth_token = config.configurable.get("authorization")
    if auth_token:
        runtime.context.authorization = "Bearer " + auth_token
        return "OK"
    return "N/A"

async def gateway(state: EPFAgentState, config: RunnableConfig, runtime: Runtime[AgentContext]) -> dict[str, Any] | Any:
    supervisor_agent = await create_supervisor_agent(runtime.context)
    output = await supervisor_agent.ainvoke(input=AgentInput(state),config=config,context=runtime.context)
    return AgentOutput(output)

load_servers()
builder = StateGraph(
    state_schema=EPFAgentState,
    context_schema=AgentContext,
    input_schema=AgentInput,
    output_schema=AgentOutput)
builder.add_node("gateway", gateway)
builder.add_conditional_edges(START, authenticate, {"OK":"gateway","N/A": END})
graph = builder.compile()