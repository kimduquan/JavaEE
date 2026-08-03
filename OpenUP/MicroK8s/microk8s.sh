grep -qxF "kernel.panic = 10" /etc/sysctl.conf || echo "kernel.panic = 10" | sudo tee -a /etc/sysctl.conf
grep -qxF "vm.overcommit_memory = 1" /etc/sysctl.conf || echo "vm.overcommit_memory = 1" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p
sudo rm -r -f /var/lib/kubelet
sudo snap install microk8s --classic
sudo microk8s status --wait-ready
./stop.sh
sudo unlink /var/lib/kubelet
sudo umount /var/lib/kubelet
./start.sh
sudo snap install kubectl --classic
sudo microk8s status --wait-ready
sudo microk8s disable ha-cluster --force
sudo microk8s enable cis-hardening
sudo microk8s enable dns
sudo microk8s enable metallb 172.23.225.250-172.23.225.254
sudo microk8s enable host-access
sudo microk8s enable registry
sudo microk8s inspect
sudo microk8s config > ~/.kube/config
kubectl label node desktop-q9gd575 node-role.kubernetes.io/control-plane=""
kubectl label node desktop-q9gd575 nvidia.com/gpu.present=true