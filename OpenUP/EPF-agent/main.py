from typing import Any
from fastapi import Depends, FastAPI, Request
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
from pydantic import BaseModel
import os
from agents import Agent, ModelSettings, Runner, AsyncOpenAI, OpenAIChatCompletionsModel, SQLiteSession
from agents.mcp import MCPServerStreamableHttp
from contextlib import asynccontextmanager
from jose import jwt
from aiocache import cached, SimpleMemoryCache
from agents.extensions.memory.encrypt_session import EncryptedSession

class RunRequest(BaseModel):
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

@asynccontextmanager
async def lifespan(app: FastAPI):
    yield

def get_claims(credentials: HTTPAuthorizationCredentials = Depends(security)):
    return jwt.decode(token=credentials.credentials)

@cached
def get_session(session_id: str):
    underlying_session = SQLiteSession(session_id=session_id)
    session = EncryptedSession(
        session_id=session_id,
        underlying_session=underlying_session,
        encryption_key=os.environ.get("SESSION_ENCRYPTION_KEY"),
        ttl=int(os.environ.get("SESSION_TTL")),
    )
    return session

@cached
def get_mcp_server(session_id: str):
    mcp_server = MCPServerStreamableHttp(
        params={
            "url":os.environ.get("MCP_SERVER_URL"),
            "headers": {
                "Authorization": authorization.get(session_id)
            }
        }
    )
    return mcp_server

@cached
def get_agent(session_id: str):
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

@app.post("/prompts/{name}")
async def run_prompt(name, request: Request, claims: dict[str, Any] = Depends(get_claims)):
    arguments = dict[str, Any]
    for param_name, param_value in request.query_params.items():
        arguments[param_name] = param_value
    session_id = str(claims["jti"])
    authorization.set(session_id, request.headers.get("Authorization"))
    mcp_server = get_mcp_server(session_id)
    prompt = await mcp_server.get_prompt(name=name, arguments=arguments)
    agent = get_agent(session_id)
    session = get_session(session_id)
    await Runner.run(starting_agent=agent, input=prompt.messages[0].content.text,session=session)