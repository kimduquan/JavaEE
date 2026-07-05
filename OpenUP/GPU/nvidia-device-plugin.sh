helm repo add nvdp https://nvidia.github.io/k8s-device-plugin
helm repo update
kubectl apply -f runtime.yaml
#helm upgrade -i nvdp nvdp/nvidia-device-plugin --namespace nvdp --create-namespace -f values.yaml --wait
helm upgrade -i nvdp nvdp/nvidia-device-plugin -f values.yaml --wait