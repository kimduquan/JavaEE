#docker run -d --rm --name epf-agent -e "MODEL_NAME=neuralmagic/Mistral-7B-Instruct-v0.3-GPTQ-4bit" -e "MODEL_BASE_URL=http://host.docker.internal:8000/v1" -e "MCP_SERVER_URL=http://host.docker.internal:9197/mcp" -e MODEL_API_KEY=EMPTY -p 8080:8080 epf-agent:1.0.0
kubectl create secret generic epf-agent --from-literal=SESSION_ENCRYPTION_KEY="$(openssl rand -hex 32)"
kubectl apply -f kubernetes.yml
kubectl wait deployment --for condition=available --timeout=60s epf-agent
kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=epf-agent
kubectl autoscale deployment epf-agent --max 1