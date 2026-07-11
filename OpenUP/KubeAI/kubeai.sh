helm repo add kubeai https://www.kubeai.org
helm repo update
kubectl create secret generic huggingface --from-literal=token=''
kubectl apply -f pvc.yaml
#kubectl apply -f job.yaml
#VOLUME_NAME=$(kubectl get pvc model-pvc -o jsonpath='{.spec.volumeName}')
#HOST_PATH=$(kubectl get pv $VOLUME_NAME -o jsonpath='{.spec.hostPath.path}')
#cp ~/gemma-4-E4B_q4_0-it.gguf $HOST_PATH
#kubectl logs -f jobs/ollama-load-model-to-pvc
helm upgrade --install kubeai kubeai/kubeai --wait -f values.yaml
helm upgrade --install --reuse-values --wait kubeai-models kubeai/models -f models/values.yaml