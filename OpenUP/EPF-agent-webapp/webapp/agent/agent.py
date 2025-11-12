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

def get_connections(server_name: str) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    connections[server_name] = StreamableHttpConnection(
        transport = 'streamable_http',
        url=os.environ["MCP_SERVER_URL"]
    )
    return connections

def get_client(server_name: str) -> MultiServerMCPClient:
    connections = get_connections(server_name=server_name)
    client = MultiServerMCPClient(
        connections=connections
    )
    return client

def get_agent_connections(server_name: str, prompt: Prompt) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    connections[server_name] = StreamableHttpConnection(
        transport = 'streamable_http',
        url=os.environ["MCP_SERVER_URL_FORMAT"].format(prompt)
    )
    return connections

def get_agent_client(server_name: str, prompt: Prompt) -> MultiServerMCPClient:
    connections = get_agent_connections(server_name=server_name, prompt=prompt)
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

async def create_mcp_agent() -> CompiledStateGraph[AgentState, AgentContext, AgentInput, AgentOutput]:
    model = get_model()
    default_server_name = "default"
    default_prompt_name = "default"
    default_agent_name = "default"
    default_client = get_client(server_name=default_server_name)
    tools: list[BaseTool] = []
    prompts = await list_prompts(client=default_client,server_name=default_server_name)
    for prompt in prompts:
        if(prompt.name != default_prompt_name):
            agent_name = prompt.name
            agent_server_name = prompt.name
            agent_prompt = await default_client.get_prompt(server_name=agent_server_name,prompt_name=prompt.name)
            agent_client = get_agent_client(server_name=agent_server_name,prompt=prompt)
            agent_tools = await load_tools(client=agent_client,server_name=agent_server_name)
            agent = create_agent(model=model,tools=agent_tools,system_prompt=agent_prompt[0].content,name=agent_name)
            tool = agent.as_tool(name=agent_name,description=prompt.description)
            tools.append(tool)

    default_prompt = await default_client.get_prompt(server_name=default_server_name,prompt_name=default_prompt_name)
    default_system_prompt: str = default_prompt[0].content
    return create_agent(
        model=model,
        tools=tools,
        system_prompt=default_system_prompt,
        state_schema=AgentState,
        context_schema=AgentContext,
        name=default_agent_name)

graph = asyncio.run(create_mcp_agent())
