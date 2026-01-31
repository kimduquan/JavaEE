./build.sh
./stop.sh
./start.sh
#docker run --name epf-agent-webapp -e "CLIENT_ID=account" -e "CLIENT_SECRET=cIkzKJZ4A7jXKpXfaxC4GaLxJAmmEqhP" -e "ISSUER=https://chipmunk-capable-prawn.ngrok-free.app/auth/realms/EPF-dev" -e "NEXTAUTH_URL=http://localhost:3000" -e "EPF_AGENT_URL=http://host.docker.internal:8123" -e "NEXTAUTH_SECRET=01263aa9dca3b11efa2bd1c73dfea942cfb11783e07b6d0e7b565c2f61f77709" -e "AUTH_REQUEST_TIMEOUT=10000" -e "JWT_MAX_AGE=300" -e "DEBUG=true" -p 3000:3000 -d epf/epf-agent-webapp:1.0.0
