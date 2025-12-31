FROM python:3.12-slim

COPY --exclude=Dockerfile --exclude=*venv --exclude=kubernetes.yml --exclude=*.sh --exclude=.env --exclude=src / /agent

WORKDIR /agent

RUN python -m venv .venv

RUN .venv/bin/python -m pip install uv

RUN .venv/bin/python -m uv sync

CMD [".venv/bin/python", "main.py"]
