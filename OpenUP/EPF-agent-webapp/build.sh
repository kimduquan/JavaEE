export NEXT_PUBLIC_NEXT_AUTH_BASE_URL=https://chipmunk-capable-prawn.ngrok-free.app
export NEXT_PUBLIC_NEXT_AUTH_BASE_PATH=/agent/api/auth
export NEXT_PUBLIC_NEXT_AUTH_REFETCH_INTERVAL=60
export COPILOTKIT_TELEMETRY_DISABLED=true
cd webapp
npm install --verbose
npm run build --verbose
cd ../
docker build -t epf/epf-agent-webapp:1.0.0 .
. ../env.sh
mvn clean package -U