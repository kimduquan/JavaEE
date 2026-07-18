call env.bat

set NEXT_PUBLIC_NEXT_AUTH_BASE_URL=http://localhost:3000
set NEXT_PUBLIC_NEXT_AUTH_BASE_PATH=/agent/api/auth
set NEXT_PUBLIC_NEXT_AUTH_REFETCH_INTERVAL=60
cd webapp
call npm install --verbose
call npm run build --verbose

call npm run dev