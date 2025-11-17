docker build -t epf-agent .
./stop.sh
./start.sh
#docker run --name epf-agent -d --rm - p 8123:8123 epf-agent