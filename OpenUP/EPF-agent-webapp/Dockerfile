FROM node:25

RUN apt-get update && apt-get install -y python3 python3-pip python3-venv && ln -sf python3 /usr/bin/python

COPY /webapp /

WORKDIR /webapp

ENV NEXT_TELEMETRY_DEBUG=1

RUN npm install --verbose && npm run build --verbose && npm run start --verbose

EXPOSE 3000

CMD npx serve@latest out
