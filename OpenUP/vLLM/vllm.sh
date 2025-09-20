helm repo add vllm https://vllm-project.github.io/production-stack
helm install vllm vllm/vllm-stack -f values.yaml