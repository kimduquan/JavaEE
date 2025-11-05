import asyncio
from typing import Any, List, Literal, Optional
from fastapi import Depends, FastAPI, Request
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
import os
from agents import Agent, ModelResponse, ModelSettings, RunContextWrapper, RunHooks, Runner, AsyncOpenAI, OpenAIChatCompletionsModel, TContext, TResponseInputItem
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
from fastapi.responses import StreamingResponse
from ag_ui.core import (
    RunAgentInput,
    EventType,
    RunStartedEvent,
    RunFinishedEvent,
    TextMessageStartEvent,
    TextMessageContentEvent,
    TextMessageEndEvent,
    ToolCallStartEvent,
    ToolCallEndEvent,
)
from ag_ui.core.events import BaseEvent
from ag_ui.encoder import EventEncoder

class AgentRequest(BaseModel):
    input: str

class AGUIRunHooks(RunHooks):
    def __init__(self, thread_id: str, run_id: str):
        self._event_queue: asyncio.Queue = asyncio.Queue()
        self._run_finished = asyncio.Event()
        self._thread_id = thread_id
        self._run_id = run_id;

    async def _emit_event(self, event: BaseEvent):
        await self._event_queue.put(event)
    
    async def on_llm_start(self, context: RunContextWrapper[TContext], agent: Agent[TContext], system_prompt: Optional[str], input_items: list[TResponseInputItem], ) -> None:
        event = TextMessageStartEvent(
            type=EventType.TEXT_MESSAGE_START,
            message_id='message_id',
            role="assistant"
        )
        await self._emit_event(event=event)
        pass

    async def on_llm_end(self, context: RunContextWrapper[TContext], agent: Agent[TContext], response: ModelResponse, ) -> None:
        event = TextMessageEndEvent(
                type=EventType.TEXT_MESSAGE_END,
                message_id='message_id'
        )
        await self._emit_event(event=event)
        pass

    async def on_agent_start(self, context: RunContextWrapper[TContext], agent: Agent) -> None:
        event = RunStartedEvent(
                type=EventType.RUN_STARTED,
                thread_id=self._thread_id,
                run_id=self._run_id
            ),
        await self._emit_event(event=event)
        pass

    async def on_agent_end(self, context: RunContextWrapper[TContext], agent: Agent, output: Any, ) -> None:
        event = RunFinishedEvent(
                type=EventType.RUN_FINISHED,
                thread_id=self._thread_id,
                run_id=self._run_id
            )
        await self._emit_event(event=event)
        self._run_finished.set()
        pass

    async def on_handoff(self, context: RunContextWrapper[TContext], from_agent: Agent, to_agent: Agent, ) -> None:
        pass

    async def on_tool_start(self, context: RunContextWrapper[TContext], agent: Agent, tool: Tool, ) -> None:
        event = ToolCallStartEvent(
            type=EventType.TOOL_CALL_START,
            tool_call_id='tool_call_id',
            tool_call_name=tool.name
        )
        await self._emit_event(event=event)
        pass

    async def on_tool_end(self, context: RunContextWrapper[TContext], agent: Agent, tool: Tool, result: str, ) -> None:
        event = ToolCallEndEvent(
            type=EventType.TOOL_CALL_END,
            tool_call_id='tool_call_id'
        )
        await self._emit_event(event=event)
        pass

    async def event_generator(self, run_agent_input: RunAgentInput, encoder: EventEncoder):
        while not self._run_finished.is_set() or not self._event_queue.empty():
            event: BaseEvent = await self._event_queue.get()
            yield encoder.encode(event)

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
    list_resource_templates: ListResourceTemplatesResult = await client_session.list_resource_templates()

    for resource in list_resources.resources:
        if(agent_request.input.find(resource.uri.unicode_string()) != -1):
            read_resource: ReadResourceResult = await client_session.read_resource(resource.uri)
            inputs = append_input(read_resource=read_resource,inputs=inputs)
    
    resource_uri_templates: list[str] = []
    for resource_template in list_resource_templates.resourceTemplates:
        resource_uri_templates.append(resource_template.uriTemplate)
    
    resource_uris: list[Any] = extract_uris_from_text(resource_uri_templates, agent_request.input)
    for resource_uri in resource_uris:
        uri = AnyUrl(url=resource_uri)
        read_resource: ReadResourceResult = await client_session.read_resource(uri)
        inputs = append_input(read_resource=read_resource,inputs=inputs)
    
    return inputs

async def get_input(run_agent_input: RunAgentInput, session_id: str) -> list[TResponseInputItem]:
    inputs: list[TResponseInputItem] = []
    for message in run_agent_input.messages:
        input = TResponseInputItem(content=message.content,role=message.role)
        inputs.append(input)
    
    client_session: ClientSession = await get_client_session(session_id)
    list_resources: ListResourcesResult = await client_session.list_resources()
    list_resource_templates: ListResourceTemplatesResult = await client_session.list_resource_templates()

    for message in run_agent_input.messages:
        for resource in list_resources.resources:
            if(message.content.find(resource.uri.unicode_string()) != -1):
                read_resource: ReadResourceResult = await client_session.read_resource(resource.uri)
                inputs = append_input(read_resource=read_resource,inputs=inputs)
        
        resource_uri_templates: list[str] = []
        for resource_template in list_resource_templates.resourceTemplates:
            resource_uri_templates.append(resource_template.uriTemplate)
        
        resource_uris: list[Any] = extract_uris_from_text(resource_uri_templates, message.content)
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

@app.post("/{name}/agentic_chat")
async def agentic_chat_endpoint(name: str, run_agent_input: RunAgentInput, request: Request, claims: dict[str, Any] = Depends(get_claims)) -> StreamingResponse:
    arguments = get_arguments(request=request)
    session_id = get_session_id(claims=claims)
    await authorization.set(session_id, request.headers.get("Authorization"))
    handoffs = await get_handoffs(session_id=session_id,name=name,arguments=arguments)
    starting_agent = await get_starting_agent(session_id=session_id,name=name,arguments=arguments,handoffs=handoffs)
    session = await get_session(session_id)
    input = await get_input(run_agent_input=run_agent_input,session_id=session_id)
    accept_header = request.headers.get("accept")
    encoder = EventEncoder(accept=accept_header)
    hooks = AGUIRunHooks()
    await Runner.run(starting_agent=starting_agent,input=input,session=session,hooks=hooks)
    return StreamingResponse(
        hooks.event_generator(run_agent_input,encoder), 
        media_type=encoder.get_content_type())