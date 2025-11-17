FROM node:lts-slim

COPY --exclude=*agent* --exclude=*src* webapp /webapp

ENV NODE_ENV=production
ENV NEXT_TELEMETRY_DEBUG=1

RUN addgroup --system --gid 1001 nodejs
RUN adduser --system --uid 1001 nextjs

WORKDIR /webapp

#USER nextjs

EXPOSE 3000

ENV PORT=3000
ENV HOSTNAME="0.0.0.0"

CMD ["npm", "run", "start", "--verbose"]
