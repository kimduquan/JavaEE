cd webapp
npm install --verbose
npm run build --verbose
cd ../
docker build -t epf-agent-webapp .
docker run --name epf-agent -d --rm -p 3000:3000 -e "CLIENT_ID=account" -e "CLIENT_SECRET=ojwWoprJT7GFoKFknEcdhTK7FFCUS0EX" -e "ISSUER=https://chipmunk-capable-prawn.ngrok-free.app/auth/realms/EPF-dev" - e "NEXTAUTH_SECRET=IaJCszU3X6BSO/5gj+ul6TnsFzohs/WyHbGbj/TEcV4=" epf-agent-webapp