FROM node:22.21.1-alpine

COPY ./webapp/.next/standalone /webapp/
COPY ./webapp/.next/static /webapp/.next/static

ENV NODE_ENV=production
ENV NEXT_TELEMETRY_DEBUG=1

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

WORKDIR /webapp

USER nextjs

EXPOSE 3000

ENV PORT=3000
ENV HOSTNAME="0.0.0.0"

CMD ["node", "server.js"]
