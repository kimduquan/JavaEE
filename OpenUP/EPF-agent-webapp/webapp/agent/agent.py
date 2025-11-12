import asyncio
from typing import TypedDict
from langchain_openai import ChatOpenAI
from langgraph.graph import MessagesState
from langgraph.graph.state import CompiledStateGraph
from langchain.agents.factory import create_agent
import os
from langchain_core.tools.base import BaseTool
from langchain_mcp_adapters.client import MultiServerMCPClient
from langchain_mcp_adapters.sessions import Connection, StreamableHttpConnection
from langchain_mcp_adapters.tools import load_mcp_tools
from mcp.types import Prompt
from langgraph_supervisor.handoff import create_handoff_tool
from langgraph_supervisor.supervisor import create_supervisor

class AgentState(MessagesState):
    """"""

class AgentInput(TypedDict):
    """"""

class AgentOutput(TypedDict):
    """"""

class AgentContext(TypedDict):
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

def get_connections(server_name: str) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    for (mcp_server_name, mcp_server_url) in mcp_server_urls:
        if(mcp_server_name == server_name):
            connections[mcp_server_name] = StreamableHttpConnection(
            transport = 'streamable_http',
            url=mcp_server_url
            )
    return connections

def get_client(server_name: str) -> MultiServerMCPClient:
    connections = get_connections(server_name=server_name)
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

async def create_main_agent(main_server_name: str, main_prompt_name: str, main_agent_name: str) -> CompiledStateGraph[AgentState, AgentContext, AgentInput, AgentOutput]:
    model = get_model()
    main_client = get_client(server_name=main_server_name)
    tools: list[BaseTool] = []
    prompts = await list_prompts(client=main_client,server_name=main_server_name)
    for prompt in prompts:
        if(prompt.name != main_prompt_name):
            agent_name = prompt.name
            agent_server_name = prompt.name
            agent_prompt = await main_client.get_prompt(server_name=agent_server_name,prompt_name=prompt.name)
            agent_client = get_client(server_name=agent_server_name,prompt=prompt)
            agent_tools = await load_tools(client=agent_client,server_name=agent_server_name)
            agent = create_agent(model=model,tools=agent_tools,system_prompt=agent_prompt[0].content,name=agent_name)
            tool = agent.as_tool(name=agent_name,description=prompt.description)
            tools.append(tool)

    main_prompt = await main_client.get_prompt(server_name=main_server_name,prompt_name=main_prompt_name)
    main_system_prompt: str = main_prompt[0].content
    return create_agent(
        model=model,
        tools=tools,
        system_prompt=main_system_prompt,
        state_schema=AgentState,
        context_schema=AgentContext,
        name=main_agent_name)

async def create_supervisor_agent() -> CompiledStateGraph[AgentState, AgentContext, AgentInput, AgentOutput]:
    model = get_model()
    handoff_tools: list[BaseTool] = []
    handoff_agents: list[CompiledStateGraph[AgentState, AgentContext, AgentInput, AgentOutput]] = []
    for mcp_server in mcp_servers:
        handoff_agent = await create_main_agent(
            main_server_name=mcp_server,
            main_prompt_name=mcp_server,
            main_agent_name=mcp_server
        )
        handoff_tool = create_handoff_tool()
        handoff_agents.append(handoff_agent)
        handoff_tools.append(handoff_tool)
    supervisor_agent = create_supervisor(
        handoff_agents,
        model=model,
        tools=handoff_tools,
        state_schema=AgentState,
        context_schema=AgentContext,
        add_handoff_messages=False,
        add_handoff_back_messages=False
    )
    return supervisor_agent.compile()

load_servers()
graph = asyncio.run(create_supervisor_agent())