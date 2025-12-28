FROM python:3.13

RUN pip install uv

COPY --exclude=Dockerfile --exclude=*venv --exclude=kubernetes.yml --exclude=*.sh --exclude=.env / /agent

WORKDIR /agent

RUN uv sync

CMD ["python", "-m", "uvicorn", "--host", "0.0.0.0", "--port", "8123", "agent:app"]
