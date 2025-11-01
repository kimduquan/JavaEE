#kubectl create secret generic vllm --from-literal=hf-token=''
#helm repo add vllm https://vllm-project.github.io/production-stack
#helm install vllm vllm/vllm-stack -f values.yaml
kubectl delete secret vllm
kubectl create secret generic vllm --from-literal=VLLM_API_KEY='EMPTY'
kubectl apply -f vllm_dev.yaml
docker run --runtime nvidia --gpus all -v ~/.cache/huggingface:/root/.cache/huggingface --env "HUGGING_FACE_HUB_TOKEN=" --env "VLLM_API_KEY=EMPTY" -p 8000:8000 --ipc=host vllm/vllm-openai:latest --model neuralmagic/Mistral-7B-Instruct-v0.3-GPTQ-4bit --gpu-memory-utilization 0.87 --max_model_len 23200
#docker run --runtime nvidia --gpus all -v ~/.cache/huggingface:/root/.cache/huggingface --env "HUGGING_FACE_HUB_TOKEN=" -p 8000:8000 --ipc=host vllm/vllm-openai:latest --model ModelCloud/Mistral-Nemo-Instruct-2407-gptq-4bit --gpu-memory-utilization 0.87