from fastapi import FastAPI
from pydantic import BaseModel
import os
from agents.extensions.models.litellm_model import LitellmModel
from agents import Agent, ModelSettings, Runner, AsyncOpenAI, OpenAIChatCompletionsModel

app = FastAPI()

class InvokeRequest(BaseModel):
    input: str

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

agent = Agent(
    name="EPF Agent",
    model=model,
    model_settings=ModelSettings(include_usage=True)
)

@app.post("/invoke")
async def query_agent(request: InvokeRequest):
    response = await agent.invoke({"input": request.input})
    return {"output_text": response.output_text}

@app.post("/run")
async def query_agent(request: RunRequest):
    response = await Runner.run(agent, request.input)
    return {"final_output": response.final_output}