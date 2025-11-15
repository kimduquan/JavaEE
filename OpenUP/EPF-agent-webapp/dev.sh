cd webapp
npm install --verbose
npm run build --verbose
cd ../
docker build -t epf-agent-webapp .