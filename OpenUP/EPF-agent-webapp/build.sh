cd webapp
npm install --verbose
npm run build --verbose
cd ../
docker build -t epf/epf-agent-webapp:1.0.0 .
. ../env.sh
mvn clean package -U