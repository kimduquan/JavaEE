FROM node:25

RUN apt-get update && apt-get install -y python3 python3-pip python3-venv && ln -sf python3 /usr/bin/python

COPY /webapp /

WORKDIR /webapp

RUN npm install --verbose

EXPOSE 3000

CMD npm run dev

#WORKDIR /workdir

#COPY --chmod=777 dev.sh dev.sh

#CMD ./dev.sh
