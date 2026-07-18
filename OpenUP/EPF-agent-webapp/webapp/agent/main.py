from datetime import datetime, timezone
from typing import Annotated, Any, TypedDict
from opentelemetry import trace
from opentelemetry.sdk.resources import Resource
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.exporter.otlp.proto.grpc.trace_exporter import OTLPSpanExporter
from opentelemetry.sdk.trace.export import BatchSpanProcessor
from fastapi import Depends, FastAPI, HTTPException, Request
from fastapi.responses import StreamingResponse
from fastapi.security import HTTPAuthorizationCredentials, HTTPBearer
from pydantic import BaseModel
from redis.asyncio import Redis as AsyncRedis
from redis import Redis as SyncRedis
import uvicorn
import os

# Configure OpenTelemetry
service_name = os.environ.get("OTEL_SERVICE_NAME", "epf-agent")
otlp_endpoint = os.environ.get("OTEL_EXPORTER_OTLP_ENDPOINT", "http://localhost:4318")
resource = Resource(
    {"service.name": service_name}
)
provider = TracerProvider(resource=resource)
otlp_exporter = OTLPSpanExporter(
    endpoint=otlp_endpoint,
    insecure=True
)
provider.add_span_processor(BatchSpanProcessor(otlp_exporter))
trace.set_tracer_provider(provider)

from langchain_openai import ChatOpenAI
from langgraph.graph.state import CompiledStateGraph, StateGraph
from langchain.agents.factory import create_agent
import os
from langchain_core.messages import SystemMessage
from langchain_core.tools.base import BaseTool
from langchain_mcp_adapters.client import MultiServerMCPClient
from langchain_mcp_adapters.sessions import Connection, StreamableHttpConnection
from langchain_mcp_adapters.tools import load_mcp_tools
from mcp.types import Prompt
from langgraph.config import RunnableConfig
from langgraph.runtime import Runtime
from langchain.agents.middleware import AgentMiddleware, SummarizationMiddleware, ModelCallLimitMiddleware, HumanInTheLoopMiddleware, ToolCallLimitMiddleware, PIIMiddleware, ToolRetryMiddleware, ModelRetryMiddleware, ContextEditingMiddleware, ClearToolUsesEdit
from langchain.agents.middleware.human_in_the_loop import InterruptOnConfig
from copilotkit import LangGraphAGUIAgent
from ag_ui.core.types import RunAgentInput
from ag_ui.encoder import EventEncoder
from langgraph.types import Checkpointer
from langgraph.store.base import BaseStore
from langgraph.cache.base import BaseCache
from uuid import UUID
import jwt
from jwt import PyJWKClient
from copilotkit import CopilotKitState, CopilotKitMiddleware
from copilotkit.langgraph import copilotkit_customize_config, copilotkit_emit_state
from langchain_mcp_adapters.interceptors import MCPToolCallRequest, ToolCallInterceptor
from aiocache import cached, Cache
from langgraph.checkpoint.redis import AsyncRedisSaver
from langgraph.checkpoint.redis.base import CHECKPOINT_PREFIX, CHECKPOINT_WRITE_PREFIX
from langgraph.store.redis.aio import AsyncRedisStore
from langgraph.store.redis.base import STORE_PREFIX, STORE_VECTOR_PREFIX
from langchain_redis import RedisCache
from opentelemetry.instrumentation.fastapi import FastAPIInstrumentor
import logging
from jwt.exceptions import PyJWTError
from starlette.status import HTTP_403_FORBIDDEN
from openinference.instrumentation.langchain import LangChainInstrumentor
from dotenv import load_dotenv

load_dotenv()

LangChainInstrumentor().instrument()

class Progress(TypedDict):
    max: float | None = None
    value: float | None = None

class EPFAgentState(CopilotKitState):
    progress: Progress | None = None

class AgentContext(BaseModel):
    __authorization: HTTPAuthorizationCredentials
    __claims: Any
    __organization: str
    __state: EPFAgentState

    def __init__(self, authorization: HTTPAuthorizationCredentials, claims: Any, organization: str, state: EPFAgentState):
        self.__authorization = authorization
        self.__claims = claims
        self.__organization = organization
        self.__state = state

    def get_authorization(self) -> HTTPAuthorizationCredentials:
        return self.__authorization

    def get_claims(self) -> Any:
        return self.__claims

    def get_organization(self) -> str:
        return self.__organization

    def get_state(self) -> EPFAgentState:
        return self.__state

class EPFAgentMiddleware(AgentMiddleware[EPFAgentState, AgentContext]):
    """"""

class UIAgentMiddleware(CopilotKitMiddleware):

    def before_agent(
            self,
            state: CopilotKitState,
            runtime: Runtime[Any],
    ) -> dict[str, Any] | None:
        try:
            return super().before_agent(state=state, runtime=runtime)
        except TypeError:
            return None

class EPFToolCallInterceptor(ToolCallInterceptor):
    async def __call__(
        self,
        request: MCPToolCallRequest,
        handler,
    ):
        context: AgentContext = request.runtime.context
        if(context and context.get_authorization()):
            authorization = context.get_authorization()
            headers = { "Authorization": authorization.scheme + " " + authorization.credentials }
            new_request = request.override(headers=headers)
            return await handler(new_request)
        return await handler(request)

DEFAULT_SERVER_NAME = "gateway"

MCP_SERVER_URLS: dict[str, str] = {
    DEFAULT_SERVER_NAME: os.environ["MCP_SERVER_URL"],
    "chrome_devtools": os.environ["CHROME_DEVTOOLS_MCP_SERVER_URL"]
}
MCP_SERVERS: list[str] = [
    DEFAULT_SERVER_NAME,
    "query",
    "persistence",
    "chrome_devtools"
]

DEBUG = ("true" == os.getenv("DEBUG", "false"))

MODEL_CALL_THREAD_LIMIT = int(os.environ["MODEL_CALL_THREAD_LIMIT"])
MODEL_CALL_RUN_LIMIT = int(os.environ["MODEL_CALL_RUN_LIMIT"])
MODEL_CALL_LIMIT = ModelCallLimitMiddleware(thread_limit=MODEL_CALL_THREAD_LIMIT, run_limit=MODEL_CALL_RUN_LIMIT, exit_behavior="error")
TOOL_CALL_THREAD_LIMIT = int(os.environ["TOOL_CALL_THREAD_LIMIT"])
TOOL_CALL_RUN_LIMIT = int(os.environ["TOOL_CALL_RUN_LIMIT"])
TOOL_CALL_LIMIT = ToolCallLimitMiddleware(thread_limit=TOOL_CALL_THREAD_LIMIT, run_limit=TOOL_CALL_RUN_LIMIT, exit_behavior="error")
PII = PIIMiddleware(pii_type="email")
TOOL_RETRY = ToolRetryMiddleware()
MODEL_RETRY = ModelRetryMiddleware()
CONTEXT_EDITING = ContextEditingMiddleware(edits=[ClearToolUsesEdit()])
HUMAN_IN_THE_LOOP = HumanInTheLoopMiddleware(interrupt_on={"persistence": InterruptOnConfig(allowed_decisions=["approve","edit","reject"])})

EPF_AGENT_NAME = "epf-agent"
SUPERVISOR_AGENT_NAME = "supervisor-agent"
SUPERVISOR_NODE_NAME = "supervisor"
AGENT_NODE_NAME = "agent-node"

SECURITY = HTTPBearer()
JWK_CLIENT = PyJWKClient(uri=os.environ["JWT_KEY_URL"])
LOGGER = logging.getLogger(__name__)
CACHE = Cache(Cache.MEMORY)

def load_servers():
    for server in MCP_SERVERS:
        if(MCP_SERVER_URLS.get(server) is None):
            MCP_SERVER_URLS[server] = os.environ["MCP_SERVER_URL_FORMAT"].format(server)

def get_connections(server_name: str, headers: dict[str, Any] | None) -> dict[str, Connection]:
    connections: dict[str, Connection] = {}
    for (mcp_server_name, mcp_server_url) in MCP_SERVER_URLS.items():
        connections[mcp_server_name] = StreamableHttpConnection(
            transport = 'streamable_http',
            url=mcp_server_url,
            headers=headers
        )
    return connections

def get_client(server_name: str, authorization: HTTPAuthorizationCredentials) -> MultiServerMCPClient:
    headers: dict[str, Any] = {}
    headers["Authorization"] = authorization.scheme + " " + authorization.credentials
    connections = get_connections(server_name=server_name, headers=headers)
    tool_interceptors = [EPFToolCallInterceptor()]
    client = MultiServerMCPClient(
        connections=connections,
        tool_interceptors=tool_interceptors
    )
    return client

async def load_tools(client: MultiServerMCPClient, server_name: str) -> list[BaseTool]:
    connection = client.connections[server_name]
    tool_interceptors = [EPFToolCallInterceptor()]
    tools = await load_mcp_tools(session=None,connection=connection,server_name=server_name, tool_interceptors=tool_interceptors)
    return tools

async def list_prompts(client: MultiServerMCPClient, server_name: str) -> list[Prompt]:
    prompts: list[Prompt] = []
    async with client.session(server_name=server_name) as session:
        list_prompts_result = await session.list_prompts()
        prompts = list_prompts_result.prompts
    return prompts

async def invoke_agent(state: dict[str, Any] | None, config: RunnableConfig, runtime: Runtime[AgentContext]) -> dict[str, Any] | Any:
    server_name: str = config["metadata"]["server_name"]
    prompt_name: str = config["metadata"]["prompt_name"]
    agent_name: str = config["metadata"]["agent_name"]
    context = runtime.context
    print(f"invoke_agent: server_name={server_name},prompt_name={prompt_name},agent_name={agent_name},organization={context.get_organization()}")
    print(f"invoke_agent: input={state}")
    client = get_client(server_name=server_name, authorization=context.get_authorization())
    prompt_contents = await client.get_prompt(server_name=server_name, prompt_name=prompt_name, arguments=state)
    context_state = context.get_state()
    messages = context_state["messages"].copy()
    for prompt_content in prompt_contents:
        messages.append(SystemMessage(content=prompt_content.content))
    sub_state = EPFAgentState(context_state)
    sub_state["messages"] = messages
    agent: CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState] = await CACHE.get(key=agent_name)
    try:
        output = await agent.ainvoke(input=sub_state, config=config, context=context)
        print(f"invoke_agent: output={output}")
        output_message = output["messages"][-1]
        print(f"invoke_agent: output_message={output_message}")
        return { "messages" : [output_message] }
    except Exception as ex:
        print(f"error:{ex.args}")
        raise ex

async def create_sub_agent_node(server_name: str, prompt_name: str, agent: CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState], organization: str) -> CompiledStateGraph[EPFAgentState, AgentContext, dict[str, Any], EPFAgentState]:
    await CACHE.set(key=agent.name, value=agent)
    graph: StateGraph[EPFAgentState, AgentContext, dict[str, Any], EPFAgentState] = StateGraph(state_schema=EPFAgentState, context_schema=AgentContext, input_schema=dict[str, Any], output_schema=EPFAgentState)
    metadata: dict[str, Any] = {}
    metadata["agent_name"] = agent.name
    metadata["server_name"] = server_name
    metadata["prompt_name"] = prompt_name
    graph.add_node("invoke_agent", invoke_agent, metadata=metadata, input_schema=dict[str, Any])
    graph.set_entry_point("invoke_agent")
    graph.set_finish_point("invoke_agent")
    checkpointer = await get_checkpointer(organization)
    store = await get_store(organization)
    cache = await get_cache(organization)
    return graph.compile(checkpointer=checkpointer, cache=cache, store=store, debug=DEBUG, name=AGENT_NODE_NAME + "-" + organization)

def as_tool(name: str, prompt: Prompt, agent: CompiledStateGraph[EPFAgentState, AgentContext, dict[str, Any], EPFAgentState]) -> BaseTool:
    arg_types: dict[str, type] | None = None
    if prompt.arguments:
        arg_types = {}
        for argument in prompt.arguments:
            if(argument.required == True):
                arg_types[argument.name] = str
            else:
                arg_types[argument.name] = str | None
    tool = agent.as_tool(name=name, description=prompt.description, arg_types=arg_types)
    return tool

async def create_sub_agent(server_name: str, agent_name: str, organization: str, authorization: HTTPAuthorizationCredentials) -> CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState]:
    print(f"crate sub agent: {agent_name}")
    client = get_client(server_name=server_name, authorization=authorization)
    tools: list[BaseTool] = await load_tools(client=client,server_name=server_name)
    for tool in tools:
        print(f"load tool:{tool.name}")
    model = await get_model(organization)
    checkpointer = await get_checkpointer(organization)
    store = await get_store(organization)
    cache = await get_cache(organization)
    return create_agent(
        model=model,
        tools=tools,
        middleware=[
            EPFAgentMiddleware()
            ],
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        checkpointer=checkpointer,
        store=store,
        debug=DEBUG,
        cache=cache,
        name=agent_name + "-" + organization)

@cached()
async def get_model(organization: str) -> ChatOpenAI:
    return ChatOpenAI(model=os.environ["OPENAI_MODEL"],base_url=os.environ["OPENAI_BASE_URL"])

@cached()
async def get_checkpointer(organization: str) -> Checkpointer:
    redis_url = os.environ["CHECKPOINTER_PERSISTENCE_URL_FORMAT"].format(organization)
    checkpoint_prefix = CHECKPOINT_PREFIX + "-" + organization
    checkpoint_write_prefix = CHECKPOINT_WRITE_PREFIX + "-" + organization
    redis_client = AsyncRedis(host=os.environ["REDIS_HOST"], password=os.environ["REDIS_PASSWORD"], single_connection_client=True)
    async with AsyncRedisSaver.from_conn_string(redis_url=redis_url, redis_client=redis_client, checkpoint_prefix=checkpoint_prefix, checkpoint_write_prefix=checkpoint_write_prefix) as checkpointer:
        await checkpointer.asetup()
        return checkpointer

@cached()
async def get_store(organization: str) -> BaseStore:
    conn_string = os.environ["STORE_PERSISTENCE_URL_FORMAT"].format(os.environ["REDIS_PASSWORD"], organization)
    store_prefix = STORE_PREFIX + "-" + organization
    vector_prefix = STORE_VECTOR_PREFIX + "-" + organization
    async with AsyncRedisStore.from_conn_string(conn_string=conn_string, store_prefix=store_prefix, vector_prefix=vector_prefix) as store:
        await store.setup()
        return store

@cached()
async def get_cache(organization: str) -> BaseCache:
    redis_url = os.environ["CACHE_PERSISTENCE_URL_FORMAT"].format(organization)
    prefix = "redis-" + organization
    redis_client = SyncRedis(host=os.environ["REDIS_HOST"], password=os.environ["REDIS_PASSWORD"], single_connection_client=True)
    redis_cache = RedisCache(redis_url=redis_url, prefix=prefix, redis_client=redis_client)
    return redis_cache

def organization_key_builder(func, *args, **kwargs):
    organization = kwargs.get("organization") or args[0]
    return organization

@cached(key_builder=organization_key_builder)
async def create_supervisor_agent(organization: str, context: AgentContext) -> CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState]:
    print(f"create supervisor agent: {organization}")
    supervisor_agent_name = SUPERVISOR_AGENT_NAME + "-" + organization
    server_name = DEFAULT_SERVER_NAME
    authorization = context.get_authorization()
    client = get_client(server_name=server_name, authorization=authorization)
    prompts = await list_prompts(client=client, server_name=server_name)
    system_prompt: str | None = None
    sub_agent_prompts: list[Prompt] = []
    for agent_prompt in prompts:
        if (agent_prompt.name == DEFAULT_SERVER_NAME):
            prompt = await client.get_prompt(server_name=server_name, prompt_name=agent_prompt.name)
            system_prompt = prompt[0].content
        else:
            sub_agent_prompts.append(agent_prompt)
    print(f"have {len(sub_agent_prompts)} sub agents")
    tools: list[BaseTool] = []
    for sub_agent_prompt in sub_agent_prompts:
        sub_agent_name = sub_agent_prompt.name
        sub_agent_server_name = sub_agent_prompt.name
        sub_agent = await create_sub_agent(server_name=sub_agent_server_name, agent_name=sub_agent_name, organization=organization, authorization=authorization)
        sub_agent_node = await create_sub_agent_node(server_name=server_name, prompt_name=sub_agent_prompt.name, agent=sub_agent, organization=organization)
        tool = as_tool(name=sub_agent_name, prompt=sub_agent_prompt, agent=sub_agent_node)
        tools.append(tool)
    model = await get_model(organization)
    checkpointer = await get_checkpointer(organization)
    store = await get_store(organization)
    cache = await get_cache(organization)
    return create_agent(
        model=model,
        tools=tools,
        system_prompt=system_prompt,
        middleware=[
            UIAgentMiddleware(),
            MODEL_CALL_LIMIT,
            TOOL_CALL_LIMIT,
            PII,
            TOOL_RETRY,
            MODEL_RETRY,
            CONTEXT_EDITING,
            HUMAN_IN_THE_LOOP,
            SummarizationMiddleware(model=model),
            EPFAgentMiddleware()
            ],
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        checkpointer=checkpointer,
        store=store,
        debug=DEBUG,
        name=supervisor_agent_name,
        cache=cache)

@cached()
async def create_supervisor(organization: str) -> CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState]:
    builder = StateGraph(
        state_schema=EPFAgentState,
        context_schema=AgentContext,
        input_schema=EPFAgentState,
        output_schema=EPFAgentState
    )
    supervisor_node_name = SUPERVISOR_NODE_NAME + "-" + organization
    builder.add_node(supervisor_node_name, supervisor_node)
    builder.set_entry_point(supervisor_node_name)
    builder.set_finish_point(supervisor_node_name)
    checkpointer = await get_checkpointer(organization)
    store = await get_store(organization)
    cache = await get_cache(organization)
    return builder.compile(checkpointer=checkpointer, cache=cache, store=store, debug=DEBUG, name=supervisor_node_name)

async def supervisor_node(state: EPFAgentState, config: RunnableConfig) -> dict[str, Any] | Any:
    organization: str = config["configurable"]["organization"]
    context = AgentContext(
        authorization=config["configurable"]["authorization"], 
        claims=config["configurable"]["claims"], 
        organization=organization, 
        state=state)
    supervisor_agent: CompiledStateGraph[EPFAgentState, AgentContext, EPFAgentState, EPFAgentState] = await create_supervisor_agent(organization, context)
    state["progress"] = Progress(max=1, value=0)
    await copilotkit_emit_state(config=config, state=state)
    output = await supervisor_agent.ainvoke(input=state, config=config, context=context)
    state["progress"]["value"] = state["progress"]["max"]
    await copilotkit_emit_state(config=config, state=state)
    message = output["messages"][-1]
    return { "messages": [message] }

async def authenticate(credentials: HTTPAuthorizationCredentials) -> Any:
    claims: Any = None
    try:
        key = JWK_CLIENT.get_signing_key_from_jwt(token=credentials.credentials)
        claims = jwt.decode(jwt=credentials.credentials, key=key, issuer=os.environ["JWT_ISSUER"])
    except PyJWTError as ex:
        LOGGER.error("[%f]jwt:%s", datetime.now(tz=timezone.utc).timestamp(), credentials.credentials)
        raise HTTPException(status_code=HTTP_403_FORBIDDEN, detail=ex.args)
    return claims

def get_organization(claims: Any) -> str:
    claims_dict: dict[str, Any] = claims
    if "organization" in claims_dict.keys():
        organization_claim: dict[str, Any] = claims_dict["organization"]
        organization_name: str = list(organization_claim.keys())[0]
        organization: str = organization_claim.get(organization_name)["id"]
        return organization
    raise HTTPException(status_code=HTTP_403_FORBIDDEN)

def get_user_id(claims: Any) -> str:
    map: dict[str, Any] = claims
    return map.get("sub")

def add_agent_endpoint(app: FastAPI, name: str, path: str = "/"):

    @app.post(path)
    async def agent_endpoint(input_data: RunAgentInput, request: Request, credentials: Annotated[HTTPAuthorizationCredentials, Depends(SECURITY)]):
        claims = await authenticate(credentials=credentials)
        organization = get_organization(claims=claims)
        LOGGER.info("thread_id:%s", input_data.thread_id)
        print(f"thread_id:{input_data.thread_id}")
        config = RunnableConfig(configurable={"authorization": credentials, "claims": claims, "organization": organization}, run_id=UUID(input_data.run_id))
        config = copilotkit_customize_config(base_config=config)
        supervisor_graph = await create_supervisor(organization)
        agent = LangGraphAGUIAgent(name=name, graph=supervisor_graph, config=config)
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

    @app.get("/health")
    def health():
        """Health check."""
        return {
            "status": "ok",
            "agent": {
                "name": name,
            }
        }

    @app.delete("/checkpoints")
    async def delete_checkpoints(credentials: Annotated[HTTPAuthorizationCredentials, Depends(SECURITY)]):
        claims = await authenticate(credentials=credentials)
        organization = get_organization(claims=claims)
        thread_id = get_user_id(claims=claims)
        checkpointer: Checkpointer = await get_checkpointer(organization)
        await checkpointer.adelete_thread(thread_id=thread_id)

    @app.delete("/cache")
    async def clear_cache(credentials: Annotated[HTTPAuthorizationCredentials, Depends(SECURITY)]):
        claims = await authenticate(credentials=credentials)
        organization = get_organization(claims=claims)
        base_cache: BaseCache = await get_cache(organization)
        await base_cache.aclear()

load_servers()
APP = FastAPI()
add_agent_endpoint(app=APP, name=EPF_AGENT_NAME)
FastAPIInstrumentor.instrument_app(app=APP, excluded_urls="/health")

if __name__ == "__main__":
    uvicorn.run(app=APP, host="0.0.0.0", port=8123)