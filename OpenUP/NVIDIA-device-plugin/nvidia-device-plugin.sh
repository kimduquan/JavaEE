helm repo add nvdp https://nvidia.github.io/k8s-device-plugin
helm repo update
kubectl apply -f runtime.yaml
helm upgrade --install nvdp nvdp/nvidia-device-plugin --version v0.20.1 -f values.yaml --wait