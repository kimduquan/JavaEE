
start /B kubectl port-forward svc/keycloak 8080 &
start /B kubectl port-forward svc/redis-master 6379 &
start /B kubectl port-forward svc/jaeger 4317 16686 &
start /B kubectl port-forward svc/epf-query 9188 &
start /B kubectl port-forward svc/epf-persistence 9181 &
::start /B kubectl port-forward svc/epf-mcp-gateway 9197 &
start /B kubectl port-forward svc/chrome-devtools-mcp 8001:8000 &

:: .venv\Scripts\python.exe main.py