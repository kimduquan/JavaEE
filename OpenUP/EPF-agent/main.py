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
from redis import Redis

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

async def get_claims(credentials: HTTPAuthorizationCredentials = Depends(security)) -> dict[str, Any]:
    signing_key = jwk_client.get_signing_key_from_jwt(token=credentials.credentials)
    return jwt.decode(token=credentials.credentials,key=signing_key.key,issuer=jwt_issuer)

@cached
async def get_session(session_id: str) -> EncryptedSession:
    underlying_session = RedisSession(session_id=session_id, redis_client=redis_client)
    session = EncryptedSession(
        session_id=session_id,
        underlying_session=underlying_session,
        encryption_key=session_id,
        ttl=int(os.environ.get("SESSION_TTL")),
    )
    return session

@cached
async def get_mcp_server(session_id: str) -> MCPServerStreamableHttp:
    mcp_server = MCPServerStreamableHttp(
        params={
            "url":os.environ.get("MCP_SERVER_URL"),
            "headers": {
                "Authorization": authorization.get(session_id)
            }
        }
    )
    await mcp_server.connect()
    return mcp_server

@cached
async def get_agent(session_id: str) -> Agent:
    mcp_server = get_mcp_server(session_id)
    agent = Agent(
        name="EPF Agent",
        mcp_servers=[mcp_server],
        model=model,
        model_settings=ModelSettings(
            tool_choice="auto",
        ),
    )
    return agent

app = FastAPI(lifespan=lifespan)

@app.post("/agents/{name}")
async def run_agent(name: str, request: Request, claims: dict[str, Any] = Depends(get_claims)):
    arguments = dict[str, Any]
    for param_name, param_value in request.query_params.items():
        arguments[param_name] = param_value
    session_id = str(claims["sub"])
    authorization.set(session_id, request.headers.get("Authorization"))
    mcp_server = await get_mcp_server(session_id)
    prompt = await mcp_server.get_prompt(name=name, arguments=arguments)
    agent = await get_agent(session_id)
    session = await get_session(session_id)
    await Runner.run(starting_agent=agent, input=prompt.messages[0].content.text,session=session)

@app.get("/sessions/items")
async def get_session_items(claims: dict[str, Any] = Depends(get_claims)):
    session_id = str(claims["sub"])
    session = await get_session(session_id)
    return await session.get_items()
    
@app.delete("/sessions/items")
async def clear_session_items(claims: dict[str, Any] = Depends(get_claims)):
    session_id = str(claims["sub"])
    session = await get_session(session_id)
    await session.clear_session()