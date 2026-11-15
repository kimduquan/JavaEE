sudo snap info k8s
sudo snap refresh k8s
sudo snap install k8s --classic --channel=1.35-classic/stable
sudo k8s bootstrap --file bootstrap-config.yaml --timeout 5m
sudo k8s status --wait-ready --timeout 5m
#sudo k8s helm upgrade ck-network cilium/cilium --version 1.18.4 --namespace kube-system --reuse-values --wait -f ck-network-all-values.yaml -f values.yaml -f ck-network-values.yaml