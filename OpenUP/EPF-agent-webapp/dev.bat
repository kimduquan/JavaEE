set NEXT_PUBLIC_NEXT_AUTH_BASE_URL=http://localhost:3000
set NEXT_PUBLIC_NEXT_AUTH_BASE_PATH=/agent/api/auth
set NEXT_PUBLIC_NEXT_AUTH_REFETCH_INTERVAL=60
cd webapp
npm install --verbose

set CLIENT_ID=
set CLIENT_SECRET=
set ISSUER=https://chipmunk-capable-prawn.ngrok-free.app/auth/realms/EPF-dev
set NEXTAUTH_URL=http://localhost:3000/agent/api/auth
set AUTH_REQUEST_TIMEOUT=10000
set JWT_MAX_AGE=300
set JWT_UPDATE_AGE=60
set NEXTAUTH_SECRET=
set COPILOTKIT_PUBLIC_LICENSE_KEY=
set EPF_AGENT_URL=http://localhost:8123
set DEBUG=true
set OIDC_PROVIDER_URL=http://localhost:8080/auth/realms/EPF-dev
npm run dev