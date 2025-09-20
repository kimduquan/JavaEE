#helm repo add vllm https://vllm-project.github.io/production-stack
#helm install vllm vllm/vllm-stack -f values.yaml
$HF_TOKEN=""
docker run --gpus all -v ~/.cache/huggingface:/root/.cache/huggingface --env "HUGGING_FACE_HUB_TOKEN=$HF_TOKEN" -p 8000:8000 --ipc=host vllm/vllm-openai:latest --model ibm-granite/granite-3.3-8b-instruct