from typing import Any
from fastapi import FastAPI
from pydantic import BaseModel
import os
from agents import Agent, ModelSettings, Runner, AsyncOpenAI, OpenAIChatCompletionsModel
from agents.mcp import MCPServerStreamableHttp
from agents.mcp.util import MCPUtil
import json

class RunRequest(BaseModel):
    input: str

class PromptRequest(BaseModel):
    arguments: dict[str, Any]

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

@app.post("/prompts/{name}")
async def run_agent(name, request: PromptRequest):
    prompt = await mcp_server.get_prompt(name=name, arguments=request.arguments)
    tools = await MCPUtil.get_function_tools(server=mcp_server,convert_schemas_to_strict=True,agent=agent)
    completion = await client.chat.completions.create(messages=prompt.messages,model=model,tools=tools)
    for choice in completion.choices:
        if('tool_calls' == choice.finish_reason):
            for tool_call in choice.message.tool_calls:
                tool_name = tool_call.function.name
                arguments = json.loads(tool_call.function.arguments)
                await mcp_server.call_tool(tool_name=tool_name, arguments=arguments)
                
