import os
from fastapi import FastAPI
import uvicorn
from opentelemetry.instrumentation import auto_instrumentation

auto_instrumentation.initialize()

LOG_LEVEL = os.environ.get("LOG_LEVEL", "info")
APP = FastAPI()
HOSTNAME = os.environ.get("HOSTNAME", "0.0.0.0")
PORT = int(os.environ.get("PORT", "9198"))

if __name__ == "__main__":
    uvicorn.run(app=APP, host=HOSTNAME, port=PORT, log_level=LOG_LEVEL)