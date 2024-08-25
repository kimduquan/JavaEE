kubectl apply -f ollama.yaml
kubectl wait deployment --for condition=available ollama
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=ollama
kubectl port-forward svc/ollama 11434 &