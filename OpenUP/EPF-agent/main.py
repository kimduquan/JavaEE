from typing import Any, List, Literal
from fastapi import Depends, FastAPI, Request
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
import os
from agents import Agent, ModelSettings, Runner, AsyncOpenAI, OpenAIChatCompletionsModel, TResponseInputItem
from agents.mcp import MCPServerStreamableHttp
from contextlib import asynccontextmanager
from jose import jwt
from aiocache import cached, SimpleMemoryCache
from agents.extensions.memory.encrypt_session import EncryptedSession
from agents.extensions.memory.redis_session import RedisSession
from jwt import PyJWKClient
from mcp.client.session import ClientSession
from pydantic import AnyUrl, BaseModel
from redis import Redis
from mcp.types import Tool
from agents.mcp.util import ToolFilterContext
from mcp.client.streamable_http import streamablehttp_client
from mcp.types import TextResourceContents, BlobResourceContents, ListResourcesResult, ListResourceTemplatesResult, ReadResourceResult
from openai.types.responses.response_input_file_param import ResponseInputFileParam
import re

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

async def agent_tool_filter(context: ToolFilterContext, tool: Tool) -> bool:
    if(tool.name.startswith(context.agent.name + ".")):
        return True
    return False

def extract_uris_from_text(templates: list[str], text: str) -> list[Any]:
    regex_patterns = list[str]
    for template in templates:
        # Escape special regex characters except braces
        escaped = re.sub(r"([.+?^$()\\|])", r"\\\1", template)
        # Replace {variable} with regex to match anything except '/'
        pattern = re.sub(r"\{[^/]+\}", r"[^/]+", escaped)
        regex_patterns.append(pattern)
    
    # Combine all patterns into a single regex with OR
    combined_regex = re.compile("|".join(regex_patterns))
    
    # Find all matches in text
    matches = combined_regex.findall(text)
    return matches

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

@cached()
async def get_client_session(session_id: str) -> ClientSession:
    read, write = await streamablehttp_client(
        url=os.environ.get("MCP_SERVER_URL"),
        headers={
            "Authorization": await authorization.get(session_id)
            })
    client_session = ClientSession(read, write)
    await client_session.initialize()
    return client_session

async def get_starting_agent(session_id: str, name: str, arguments: dict[str, Any], handoffs: list[Agent[Any]]) -> Agent[Any]:
    mcp_server: MCPServerStreamableHttp = await get_mcp_server(session_id)
    prompt = await mcp_server.get_prompt(name=name, arguments=arguments)
    instructions = prompt.messages[0].content.text
    starting_agent = Agent(
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
    return starting_agent

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

def append_input(read_resource: ReadResourceResult, inputs: list[TResponseInputItem]) -> list[TResponseInputItem]:
    for resource_content in read_resource.contents:
        if(isinstance(resource_content, TextResourceContents)):
            text_contents = TextResourceContents(resource_content)
            input = TResponseInputItem(content=text_contents.text,role=Literal('user'))
            inputs.append(input)
        elif(isinstance(resource_content, BlobResourceContents)):
            blob_contents = BlobResourceContents(resource_content)
            file = ResponseInputFileParam(
                file_data=blob_contents.blob,
                file_id=blob_contents.uri.unicode_string(),
                file_url=blob_contents.uri.unicode_string(),
                filename=blob_contents.uri.unicode_string()
                )
            file_contents: List[ResponseInputFileParam] = []
            file_contents.append(file)
            input = TResponseInputItem(content=file_contents,role=Literal('user'))
            inputs.append(input)
    return inputs

async def get_input(agent_request: AgentRequest, session_id: str) -> list[TResponseInputItem]:
    inputs: list[TResponseInputItem] = []
    client_session: ClientSession = await get_client_session(session_id)
    list_resources: ListResourcesResult = await client_session.list_resources()
    for resource in list_resources.resources:
        if(agent_request.input.find(resource.uri.unicode_string()) != -1):
            read_resource: ReadResourceResult = await client_session.read_resource(resource.uri)
            inputs = append_input(read_resource=read_resource,inputs=inputs)
    list_resource_templates: ListResourceTemplatesResult = await client_session.list_resource_templates()
    resource_uri_templates: list[str] = []
    for resource_template in list_resource_templates.resourceTemplates:
        resource_uri_templates.append(resource_template.uriTemplate)
    resource_uris: list[Any] = extract_uris_from_text(resource_uri_templates, agent_request.input)
    for resource_uri in resource_uris:
        uri = AnyUrl(url=resource_uri)
        read_resource: ReadResourceResult = await client_session.read_resource(uri)
        inputs = append_input(read_resource=read_resource,inputs=inputs)
    return inputs

def get_arguments(request: Request) -> dict[str, str]:
    arguments: dict[str, str] = {}
    for param_name, param_value in request.query_params.items():
        arguments[param_name] = param_value
    return arguments

def get_session_id(claims: dict[str, Any]) -> str:
    session_id = str(claims["sub"])
    return session_id

app = FastAPI(lifespan=lifespan)

@app.post("/agents/{name}")
async def run_agent(name: str, request: Request, agent_request: AgentRequest, claims: dict[str, Any] = Depends(get_claims)):
    arguments = get_arguments(request=request)
    session_id = get_session_id(claims=claims)
    await authorization.set(session_id, request.headers.get("Authorization"))
    handoffs = await get_handoffs(session_id=session_id,name=name,arguments=arguments)
    starting_agent = await get_starting_agent(session_id=session_id,name=name,arguments=arguments,handoffs=handoffs)
    session = await get_session(session_id)
    input = await get_input(agent_request=agent_request,session_id=session_id)
    run_result = await Runner.run(starting_agent=starting_agent,input=input,session=session)
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