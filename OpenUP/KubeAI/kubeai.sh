helm repo add kubeai https://www.kubeai.org
helm repo update
helm upgrade --install kubeai kubeai/kubeai --wait