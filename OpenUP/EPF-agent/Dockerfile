FROM python:3.14.0-slim

COPY requirements.txt requirements.txt

RUN pip install --no-cache-dir --upgrade -r requirements.txt


COPY main.py main.py


CMD ["fastapi", "run", "main.py", "--port", "8080"]