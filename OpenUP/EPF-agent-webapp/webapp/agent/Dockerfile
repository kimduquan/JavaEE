FROM python:3.13

COPY --exclude=Dockerfile --exclude=*venv --exclude=kubernetes.yml --exclude=*.sh --exclude=.env / /agent

WORKDIR /agent

RUN pip install -r requirements.txt

CMD ["python", "-m", "uvicorn", "--host", "0.0.0.0", "--port", "8123", "agent:app"]
