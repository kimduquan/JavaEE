$helm repo add nvdp https://nvidia.github.io/k8s-device-plugin
$helm repo update
$kubectl apply -f runtime.yaml
# https://github.com/NVIDIA/k8s-device-plugin/blob/v0.20.2/deployments/helm/nvidia-device-plugin/values.yaml
$helm upgrade --install nvdp nvdp/nvidia-device-plugin --version 0.20.2 -f values.yaml --wait