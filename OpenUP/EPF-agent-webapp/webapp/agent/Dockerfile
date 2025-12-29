FROM python:3.13

COPY --exclude=Dockerfile --exclude=*venv --exclude=kubernetes.yml --exclude=*.sh --exclude=.env / /agent

WORKDIR /agent

RUN python -m venv .venv

RUN .venv/bin/python -m pip install uv

RUN .venv/bin/python -m uv sync

CMD [".venv/bin/python", "main.py"]
