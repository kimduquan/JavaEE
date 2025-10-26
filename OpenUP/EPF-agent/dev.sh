docker build -t epf-agent:1.0.0 .
kubectl delete secret epf-agent
kubectl create secret generic epf-agent --from-literal=OPENAI_API_KEY="$(openssl rand -hex 32)"
kubectl apply -f kubernetes.yml
kubectl wait deployment --for condition=available --timeout=60s epf-agent
kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=epf-agent
kubectl autoscale deployment epf-agent --max 2