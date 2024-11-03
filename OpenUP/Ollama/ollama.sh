docker run -d --gpus=all -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama -e OLLAMA_KEEP_ALIVE='24h' -e OLLAMA_NUM_PARALLEL='1' -e OLLAMA_MAX_QUEUE='86400' -e OLLAMA_MAX_LOADED_MODELS='3'
docker exec -it ollama ollama pull mxbai-embed-large
docker exec -it ollama ollama pull granite3-dense:8b-instruct-q4_0
kubectl get nodes -o wide
kubectl apply -f ollama.yaml
kubectl wait deployment --for condition=available ollama
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=ollama