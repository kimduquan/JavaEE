sudo snap install microk8s --classic
sudo snap install kubectl --classic
sudo microk8s status --wait-ready
sudo microk8s enable cis-hardening
sudo microk8s enable metallb 10.64.140.43-10.64.140.49
sudo microk8s enable registry
sudo microk8s enable nvidia
sudo microk8s config > ~/.kube/config