docker build -t epf-agent:1.0.0 .
#docker run -d --rm --name epf-agent -e "MODEL_NAME=hosted_vllm/neuralmagic/Mistral-7B-Instruct-v0.3-GPTQ-4bit" -e "MODEL_BASE_URL=http://host.docker.internal:8000/v1" -e MODEL_API_KEY=EMPTY -p 8080:8080 epf-agent:1.0.0
kubectl delete secret vllm
kubectl delete hpa epf-agent
kubectl create secret generic vllm --from-literal=MODEL_API_KEY="$(openssl rand -hex 32)"
kubectl apply -f kubernetes.yml
kubectl wait deployment --for condition=available --timeout=60s epf-agent
kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=epf-agent
kubectl autoscale deployment epf-agent --max 2