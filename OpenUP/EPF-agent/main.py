from fastapi import FastAPI
from pydantic import BaseModel
import os
from agents import Agent, ModelSettings, Runner, AsyncOpenAI, OpenAIChatCompletionsModel
from agents.mcp import MCPServerStreamableHttp

class RunRequest(BaseModel):
    input: str

app = FastAPI()

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

@app.post("/run")
async def run_agent(request: RunRequest):
    response = await Runner.run(agent, request.input)
    return {"final_output": response.final_output}