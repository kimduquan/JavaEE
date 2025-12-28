FROM python:3.13

COPY --exclude=Dockerfile --exclude=*venv --exclude=kubernetes.yml --exclude=*.sh --exclude=.env / /agent

WORKDIR /agent

RUN python -m pip install uv

RUN python -m uv sync

CMD ["python", "-m", "uvicorn", "--host", "0.0.0.0", "--port", "8123", "agent:app"]
