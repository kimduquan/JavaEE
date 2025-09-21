#helm repo add vllm https://vllm-project.github.io/production-stack
#helm install vllm vllm/vllm-stack -f values.yaml
docker run --runtime nvidia --gpus all -v ~/.cache/huggingface:/root/.cache/huggingface --env "HUGGING_FACE_HUB_TOKEN=" -p 8000:8000 --ipc=host vllm/vllm-openai:latest --model neuralmagic/Mistral-7B-Instruct-v0.3-GPTQ-4bit --gpu-memory-utilization 0.87 --max_model_len 23296