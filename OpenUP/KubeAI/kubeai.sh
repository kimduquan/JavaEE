helm repo add kubeai https://www.kubeai.org
helm repo update
kubectl create secret generic huggingface --from-literal=token=''
kubectl apply -f pvc.yaml
kubectl apply -f job.yaml
kubectl logs -f jobs/ollama-load-model-to-pvc
kubectl wait --for=condition=complete job/ollama-load-model-to-pvc
helm upgrade --install kubeai kubeai/kubeai --wait -f values.yaml
helm upgrade --install --reuse-values --wait kubeai-models kubeai/models -f models/values.yaml