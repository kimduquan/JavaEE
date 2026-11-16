. ../env.sh ../
sudo snap info k8s
sudo snap refresh k8s
sudo snap install k8s --classic --channel=1.35-classic/stable
sudo mkdir "/etc/containerd/hosts.d/$EPF_CLUSTER_IMAGE_REGISTRY"
sudo cp ../hosts.toml "/etc/containerd/hosts.d/$EPF_CLUSTER_IMAGE_REGISTRY"
sudo k8s bootstrap --file bootstrap-config.yaml --timeout 5m
./cilium.sh
sudo k8s status --wait-ready --timeout 5m
sudo k8s kubectl annotate namespace default clustermesh.cilium.io/global="true"
sudo snap install kubectl --classic
sudo k8s config > ~/.kube/config