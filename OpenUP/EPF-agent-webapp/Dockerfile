FROM node:lts-slim

RUN apt-get update && apt-get upgrade && apt-get install -y python3 python3-pip python3-venv && ln -sf python3 /usr/bin/python

WORKDIR /webapp

COPY webapp/.next/standalone /webapp
COPY webapp/agent /webapp

ENV NODE_ENV=production
ENV NEXT_TELEMETRY_DEBUG=1

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

#RUN npm install --verbose && npm run build --verbose && npm run dev:agent --verbose

RUN cd /webapp/agent && npx @langchain/langgraph-cli dev --port 8123 --no-browser

WORKDIR /standalone

USER nextjs

EXPOSE 3000

ENV PORT=3000
ENV HOSTNAME="0.0.0.0"

CMD ["node", "server.js"]
