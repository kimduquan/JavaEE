FROM python:3.12-slim

COPY --exclude=Dockerfile --exclude=*venv / /agent

WORKDIR /agent

RUN python -m venv .venv

RUN .venv/bin/python -m pip install uv

RUN .venv/bin/python -m uv sync

CMD [".venv/bin/python", "main.py"]
