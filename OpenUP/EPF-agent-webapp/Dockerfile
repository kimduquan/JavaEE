FROM node:25

#SHELL ["/bin/bash", "-c"]

RUN apt-get update && apt-get install -y python3 python3-pip python3-venv && ln -sf python3 /usr/bin/python

#VOLUME ["/epf"]

#WORKDIR /epf

#RUN npx copilotkit@latest create -f langgraph-py -n epf-agent-webapp -q

#WORKDIR /epf/epf-agent-webapp

#RUN npm install --verbose

#ENV OPENAI_API_KEY=EMPTY

#RUN npm run dev

EXPOSE 3000

#CMD npx copilotkit@latest create -f langgraph-py -n epf-agent-webapp -q

WORKDIR /workdir

COPY --chmod=777 dev.sh dev.sh

CMD ./dev.sh

#ENTRYPOINT ["/bin/bash"]