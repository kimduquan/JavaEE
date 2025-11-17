FROM python:3.14-slim

COPY --exclude=Dockerfile / /agent

WORKDIR /agent

RUN pip install -r requirements.txt

CMD ["langgraph", "dev", "--host", "0.0.0.0", "--port", "8123", "--no-reload", "--no-browser"]