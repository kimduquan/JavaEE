cd webapp
npm install --verbose
npm run build --verbose
cd ../
docker build -t epf/epf-agent-webapp .
../env.sh .
mvn clean install -U