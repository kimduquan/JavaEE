FROM node:25

RUN apt-get update && apt-get install -y python3 python3-pip python3-venv && ln -sf python3 /usr/bin/python

COPY /webapp /

WORKDIR /webapp

RUN npm install --verbose && npm run build

EXPOSE 3000

CMD npm run start

#WORKDIR /workdir

#COPY --chmod=777 dev.sh dev.sh

#CMD ./dev.sh
