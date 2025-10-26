docker build -t epf-agent:1.0.0 .
#docker run -d -e "MODEL=hosted_vllm/neuralmagic/Mistral-7B-Instruct-v0.3-GPTQ-4bit" -e MODEL_API_BASE=host.docker.internal:8000/v1 -p 8080:8080 epf-agent:1.0.0
kubectl delete secret epf-agent
kubectl delete hpa epf-agent
kubectl create secret generic epf-agent --from-literal=MODEL_API_KEY="$(openssl rand -hex 32)"
kubectl apply -f kubernetes.yml
kubectl wait deployment --for condition=available --timeout=60s epf-agent
kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=epf-agent
kubectl autoscale deployment epf-agent --max 2