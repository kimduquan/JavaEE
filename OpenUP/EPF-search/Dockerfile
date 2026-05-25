FROM python:3.12-slim

COPY /src --exclude=*.properties /search

WORKDIR /search

RUN python -m venv .venv

RUN .venv/bin/python -m pip install --no-cache-dir -r requirements.txt

EXPOSE 9198

ENV PORT=9198
ENV HOSTNAME="0.0.0.0"

CMD [".venv/bin/python", "main.py"]