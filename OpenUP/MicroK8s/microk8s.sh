sudo snap install microk8s --classic
sudo microk8s status --wait-ready
sudo microk8s enable dns
sudo microk8s enable metallb
sudo microk8s enable rbac
sudo microk8s enable registry
sudo microk8s enable hostpath-storage
sudo microk8s enable gpu