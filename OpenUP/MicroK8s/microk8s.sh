sudo snap install microk8s --classic
sudo snap install kubectl --classic
sudo microk8s status --wait-ready
sudo microk8s disable ha-cluster --force
sudo microk8s enable cis-hardening
sudo microk8s enable metallb 172.23.225.250-172.23.225.254
sudo microk8s enable host-access
sudo microk8s enable registry
sudo microk8s enable nvidia
sudo microk8s config > ~/.kube/config
kubectl label node desktop-q9gd575 node-role.kubernetes.io/control-plane=""