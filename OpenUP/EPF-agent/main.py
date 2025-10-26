from fastapi import FastAPI
from pydantic import BaseModel
import os
from agents.models import LiteLLMModel
from agents import Agent, ModelSettings

app = FastAPI()

class InvokeRequest(BaseModel):
    input: str

model = LiteLLMModel(
    model=os.environ.get("MODEL"),
    api_base=os.environ.get("MODEL_API_BASE"),
    api_key=os.environ.get("MODEL_API_KEY"),
)

agent = Agent(
    name="EPF Agent",
    model=model,
    model_settings=ModelSettings(include_usage=True)
)

@app.post("/invoke")
async def query_agent(request: InvokeRequest):
    response = await agent.invoke({"input": request.input})
    return {"output": response.output_text}