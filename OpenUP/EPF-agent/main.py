from typing import Any
from fastapi import Depends, FastAPI, Request
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
import os
from agents import Agent, ModelSettings, Runner, AsyncOpenAI, OpenAIChatCompletionsModel
from agents.mcp import MCPServerStreamableHttp
from contextlib import asynccontextmanager
from jose import jwt
from aiocache import cached, SimpleMemoryCache
from agents.extensions.memory.encrypt_session import EncryptedSession
from agents.extensions.memory.redis_session import RedisSession
from jwt import PyJWKClient
from pydantic import BaseModel
from redis import Redis
from agents.mcp.util import MCPTool, ToolFilterContext

class AgentRequest(BaseModel):
    input: str

client = AsyncOpenAI(
    base_url=os.environ.get("MODEL_BASE_URL"),
    api_key=os.environ.get("MODEL_API_KEY"),
)

model = OpenAIChatCompletionsModel(
    model=os.environ.get("MODEL_NAME"),
    openai_client=client,
)

authorization = SimpleMemoryCache()

security = HTTPBearer()

redis_client = Redis(host=os.environ.get("REDIS_HOST"), password=os.environ.get("REDIS_PASSWORD"))

jwk_client = PyJWKClient(os.environ.get("JWT_VERIFY_PUBLICKEY_LOCATION"))

jwt_issuer = os.environ.get("JWT_VERIFY_ISSUER")

@asynccontextmanager
async def lifespan(app: FastAPI):
    yield

def get_claims(credentials: HTTPAuthorizationCredentials = Depends(security)) -> dict[str, Any]:
    signing_key = jwk_client.get_signing_key_from_jwt(token=credentials.credentials)
    return jwt.decode(token=credentials.credentials,key=signing_key.key,issuer=jwt_issuer)

async def agent_tool_filter(context: ToolFilterContext, tool: MCPTool) -> bool:
    if(tool.name.startswith(context.agent.name + ".")):
        return True
    return False

@cached()
async def get_session(session_id: str) -> EncryptedSession:
    underlying_session = RedisSession(session_id=session_id, redis_client=redis_client)
    session = EncryptedSession(
        session_id=session_id,
        underlying_session=underlying_session,
        encryption_key=session_id,
        ttl=int(os.environ.get("SESSION_TTL")),
    )
    return session

@cached()
async def get_mcp_server(session_id: str) -> MCPServerStreamableHttp:
    mcp_server = MCPServerStreamableHttp(
        tool_filter=agent_tool_filter,
        params={
            "url":os.environ.get("MCP_SERVER_URL"),
            "headers": {
                "Authorization": await authorization.get(session_id)
            }
        }
    )
    await mcp_server.connect()
    return mcp_server

async def get_agent(session_id: str, name: str, arguments: dict[str, Any], handoffs: list[Agent[Any]]) -> Agent[Any]:
    mcp_server: MCPServerStreamableHttp = await get_mcp_server(session_id)
    prompt = await mcp_server.get_prompt(name=name, arguments=arguments)
    instructions = prompt.messages[0].content.text
    agent = Agent(
        name=name,
        handoff_description=prompt.description,
        mcp_servers=[mcp_server],
        instructions=instructions,
        handoffs=handoffs,
        model=model,
        model_settings=ModelSettings(
            tool_choice="auto",
        ),
    )
    return agent

async def get_handoffs(session_id: str, name: str, arguments: dict[str, Any]) -> list[Agent[Any]]:
    handoffs: list[Agent[Any]] = []
    mcp_server: MCPServerStreamableHttp = await get_mcp_server(session_id)
    list_prompts = await mcp_server.list_prompts()
    for prompt in list_prompts.prompts:
        if(name != prompt.name):
            agent_prompt = await mcp_server.get_prompt(name=name, arguments=arguments)
            handoff_description = agent_prompt.description
            instructions = agent_prompt.messages[0].content.text
            handoff_agent = Agent(
                name=name,
                handoff_description=handoff_description,
                mcp_servers=[mcp_server],
                instructions=instructions,
                model=model,
                model_settings=ModelSettings(
                    tool_choice="auto",
                ),
            )
            handoffs.append(handoff_agent)
    return handoffs

app = FastAPI(lifespan=lifespan)

@app.post("/agents/{name}")
async def run_agent(name: str, request: Request, agent_request: AgentRequest, claims: dict[str, Any] = Depends(get_claims)):
    arguments: dict[str, str] = {}
    for param_name, param_value in request.query_params.items():
        arguments[param_name] = param_value
    session_id = str(claims["sub"])
    await authorization.set(session_id, request.headers.get("Authorization"))
    handoffs = await get_handoffs(session_id=session_id,name=name,arguments=arguments)
    starting_agent = await get_agent(session_id=session_id,name=name,arguments=arguments,handoffs=handoffs)
    session = await get_session(session_id)
    run_result = await Runner.run(starting_agent=starting_agent,input=agent_request.input,session=session)
    return run_result

@app.get("/sessions/items")
async def get_session_items(claims: dict[str, Any] = Depends(get_claims)):
    session_id = str(claims["sub"])
    session = await get_session(session_id)
    return session.get_items()
    
@app.delete("/sessions/items")
async def clear_session_items(claims: dict[str, Any] = Depends(get_claims)):
    session_id = str(claims["sub"])
    session = await get_session(session_id)
    session.clear_session()