helm repo add kubeai https://www.kubeai.org
helm repo update
kubectl create secret generic huggingface --from-literal=token=''
helm upgrade --install kubeai kubeai/kubeai --wait