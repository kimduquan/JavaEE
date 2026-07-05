sudo snap install microk8s --classic
sudo snap install kubectl --classic
sudo microk8s status --wait-ready
sudo microk8s disable ha-cluster --force
sudo microk8s inspect
sudo microk8s enable cis-hardening
sudo microk8s enable metallb 172.23.225.250-172.23.225.254
sudo microk8s enable host-access
sudo microk8s enable registry
sudo microk8s config > ~/.kube/config
kubectl label node desktop-q9gd575 node-role.kubernetes.io/control-plane=""
#kubectl label node desktop-q9gd575 nvidia.com/gpu.present=true
#kubectl label node desktop-q9gd575 feature.node.kubernetes.io/pci-10de.present=true --overwrite
#sudo nvidia-ctk runtime configure --runtime=containerd --config=/var/snap/microk8s/current/args/containerd-template.toml
#kubectl label node desktop-q9gd575 feature.node.kubernetes.io/gpu.present=true --overwrite
#kubectl run --rm -it --image=docker.io/nvidia/cuda:13.2.0-base-ubuntu24.04 test nvidia-smi