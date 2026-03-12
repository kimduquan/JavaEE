
start /B kubectl port-forward svc/epf-mcp-gateway 9197 &
start /B kubectl port-forward svc/keycloak 8080 &
start /B kubectl port-forward svc/redis-master 6379 &
start /B kubectl port-forward svc/jaeger 4317 &

:: .venv\Scripts\python.exe main.py