import asyncio
import os
from fastapi import FastAPI
from opentelemetry.instrumentation import auto_instrumentation

auto_instrumentation.initialize()

LOG_LEVEL = os.environ.get("LOG_LEVEL", "debug")
APP = FastAPI()
HOSTNAME = os.environ.get("HOSTNAME", "0.0.0.0")
PORT = int(os.environ.get("PORT", "8002"))

if __name__ == "__main__":
    