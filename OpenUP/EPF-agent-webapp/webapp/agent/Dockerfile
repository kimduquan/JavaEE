FROM python:3.13

COPY --exclude=Dockerfile --exclude=*venv --exclude=kubernetes.yml --exclude=*.sh / /agent

WORKDIR /agent

RUN pip install -r requirements.txt

CMD ["langgraph", "dev", "--host", "0.0.0.0", "--port", "8123", "--no-reload", "--no-browser"]
