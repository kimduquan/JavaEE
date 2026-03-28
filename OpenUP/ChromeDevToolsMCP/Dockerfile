FROM node:22.22.2-alpine

RUN apk add --no-cache python3 py3-pip

COPY requirements.txt /chrome-devtools-mcp/
COPY main.py /chrome-devtools-mcp/

WORKDIR /chrome-devtools-mcp

RUN python -m venv .venv

RUN .venv/bin/python -m pip install --no-cache-dir -r requirements.txt

CMD [".venv/bin/python", "main.py"]
