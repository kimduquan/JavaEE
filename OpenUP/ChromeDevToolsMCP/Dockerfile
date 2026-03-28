FROM node:22.22.2-alpine

RUN apk add --no-cache --verbose python3 py3-pip

COPY chrome-devtools-mcp /chrome-devtools-mcp

WORKDIR /chrome-devtools-mcp

RUN npm i --verbose chrome-devtools-mcp

RUN python -m venv .venv

RUN .venv/bin/python -m pip install --no-cache-dir -r requirements.txt

EXPOSE 8000

ENV PORT=8000
ENV HOSTNAME="0.0.0.0"

CMD [".venv/bin/python", "main.py"]
