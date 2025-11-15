FROM node:lts-alpine3.22

RUN apt-get update && apt-get install -y python3 python3-pip python3-venv && ln -sf python3 /usr/bin/python

COPY /webapp /

WORKDIR /webapp

ENV NODE_ENV=production
ENV NEXT_TELEMETRY_DEBUG=1

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

RUN npm install --verbose && npm run build --verbose && npm run dev:agent --verbose

USER nextjs

EXPOSE 3000

ENV PORT=3000
ENV HOSTNAME="0.0.0.0"

CMD ["node", "server.js"]
