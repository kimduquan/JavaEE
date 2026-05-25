FROM python:3.12-slim

COPY /src /search

WORKDIR /search

RUN python -m venv .venv

RUN .venv/bin/python -m pip install -r requirements.txt

RUN .venv/bin/python -m uv sync

CMD [".venv/bin/python", "main.py"]