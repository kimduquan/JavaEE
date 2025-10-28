from typing import Any
from fastapi import FastAPI
from pydantic import BaseModel
import os
from agents import Agent, ModelSettings, Runner, AsyncOpenAI, OpenAIChatCompletionsModel
from agents.mcp import MCPServerStreamableHttp
from contextlib import asynccontextmanager

class RunRequest(BaseModel):
    input: str

class PromptRequest(BaseModel):
    arguments: dict[str, Any]

client = AsyncOpenAI(
    base_url=os.environ.get("MODEL_BASE_URL"),
    api_key=os.environ.get("MODEL_API_KEY"),
)

model = OpenAIChatCompletionsModel(
    model=os.environ.get("MODEL_NAME"),
    openai_client=client,
)

mcp_server = MCPServerStreamableHttp(
    params={
        "url":os.environ.get("MCP_SERVER_URL"),
    }
)

agent = Agent(
    name="EPF Agent",
    mcp_servers=[mcp_server],
    model=model,
    model_settings=ModelSettings(
        tool_choice="auto"
    ),
)

@asynccontextmanager
async def lifespan(app: FastAPI):
    await mcp_server.connect()
    yield
    await mcp_server.cleanup()

app = FastAPI(lifespan=lifespan)

@app.post("/run")
async def run_agent(request: RunRequest):
    response = await Runner.run(starting_agent=agent,input=request.input)
    return {"final_output": response.final_output}

@app.post("/prompts/{name}")
async def run_prompt(name, request: PromptRequest):
    prompt = await mcp_server.get_prompt(name=name, arguments=request.arguments)
    await Runner.run(starting_agent=agent, input=prompt.messages[0].content.text)
                
