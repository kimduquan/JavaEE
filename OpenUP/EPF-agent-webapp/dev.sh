cd webapp
npm install --verbose
npm run build --verbose
cd ../
docker build -t epf/epf-agent-webapp .
./stop.sh
./start.sh
#docker run --name epf-agent -d --rm -p 3000:3000 -e "CLIENT_ID=account" -e "CLIENT_SECRET=ojwWoprJT7GFoKFknEcdhTK7FFCUS0EX" -e "ISSUER=https://chipmunk-capable-prawn.ngrok-free.app/auth/realms/EPF-dev" -e "NEXTAUTH_SECRET=IaJCszU3X6BSO/5gj+ul6TnsFzohs/WyHbGbj/TEcV4=" -e "NEXTAUTH_URL=http://localhost:3000" -e "LANGGRAPH_DEPLOYMENT_URL=http://host.docker.internal:8123" epf-agent-webapp
#export CLIENT_ID=account
#export CLIENT_SECRET=ojwWoprJT7GFoKFknEcdhTK7FFCUS0EX
#export ISSUER=https://chipmunk-capable-prawn.ngrok-free.app/auth/realms/EPF-dev
#export NEXTAUTH_SECRET=IaJCszU3X6BSO/5gj+ul6TnsFzohs/WyHbGbj/TEcV4=
#export NEXTAUTH_URL=http://localhost:3000
#npm run start --verbose
