kubectl delete -f ollama.yaml
docker rm ollama
docker run -d -e OLLAMA_KEEP_ALIVE='24h' -e OLLAMA_NUM_PARALLEL='1' -e OLLAMA_MAX_QUEUE='86400' -e OLLAMA_MAX_LOADED_MODELS='3' --gpus=all -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama
docker exec -it ollama ollama pull granite-embedding:278m
docker exec -it ollama ollama pull granite3.1-dense
kubectl get nodes -o wide
kubectl apply -f ollama.yaml
kubectl wait deployment --for condition=available ollama
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=ollama