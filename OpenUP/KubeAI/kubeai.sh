helm repo add kubeai https://www.kubeai.org
helm repo update
kubectl create secret generic huggingface --from-literal=token=''
kubectl apply -f pvc.yaml
helm upgrade --install kubeai kubeai/kubeai --wait -f values.yaml