grep -qxF "kernel.panic = 10" /etc/sysctl.conf || echo "kernel.panic = 10" | sudo tee -a /etc/sysctl.conf
grep -qxF "kernel.panic_on_oops = 1" /etc/sysctl.conf || echo "kernel.panic_on_oops = 1" | sudo tee -a /etc/sysctl.conf
grep -qxF "vm.overcommit_memory = 1" /etc/sysctl.conf || echo "vm.overcommit_memory = 1" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p

sudo snap install microk8s --classic
sudo microk8s status --wait-ready
sudo microk8s inspect
NODENAME=$(cat /etc/hostname)
NETWORK_INTERFACE=enp2s0
sudo microk8s kubectl annotate node $NODENAME "ovn.kubernetes.io/tunnel_interface=$NETWORK_INTERFACE"
sudo microk8s enable kube-ovn --force

sudo microk8s status --wait-ready
sudo microk8s inspect

#kubectl label node ubuntu node-role.kubernetes.io/worker=""