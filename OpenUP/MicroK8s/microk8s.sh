grep -qxF "kernel.panic = 10" /etc/sysctl.conf || echo "kernel.panic = 10" | sudo tee -a /etc/sysctl.conf
grep -qxF "vm.overcommit_memory = 1" /etc/sysctl.conf || echo "vm.overcommit_memory = 1" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p
sudo hostnamectl set-hostname desktop-q9gd575
#sudo rm /etc/resolv.conf
#sudo ln -s /run/systemd/resolve/resolv.conf /etc/resolv.conf
sudo snap install microk8s --classic
sudo microk8s status --wait-ready
sudo microk8s inspect
#sudo mkdir -p /var/snap/microk8s/current/args/certs.d/172.23.225.247:32000
#sudo cp hosts.toml /var/snap/microk8s/current/args/certs.d/172.23.225.247:32000
#./stop.sh
#sudo unlink /var/lib/kubelet
#sudo umount /var/lib/kubelet
#./start.sh
sudo snap install kubectl --classic
sudo microk8s kubectl annotate node desktop-q9gd575 ovn.kubernetes.io/tunnel_interface=eth0 --overwrite
sudo microk8s enable kube-ovn --force
sudo microk8s status --wait-ready
sudo microk8s inspect
sudo microk8s enable cis-hardening
sudo microk8s enable dns
sudo microk8s enable metallb 192.168.1.240-192.168.1.254
sudo microk8s enable registry
sudo microk8s inspect
sudo microk8s kubectl label node desktop-q9gd575 node-role.kubernetes.io/control-plane=""
sudo microk8s kubectl label node desktop-q9gd575 nvidia.com/gpu.present=true
sudo microk8s config > ~/.kube/config