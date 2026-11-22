grep -qxF "kernel.panic = 10" /etc/sysctl.conf || echo "kernel.panic = 10" | sudo tee -a /etc/sysctl.conf
grep -qxF "kernel.panic_on_oops = 1" /etc/sysctl.conf || echo "kernel.panic_on_oops = 1" | sudo tee -a /etc/sysctl.conf
grep -qxF "vm.overcommit_memory = 1" /etc/sysctl.conf || echo "vm.overcommit_memory = 1" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p

sudo snap install microk8s --classic
sudo microk8s status --wait-ready
sudo microk8s inspect
sudo sed -i '/\[plugins\."io\.containerd\.grpc\.v1\.cri"\]/a \  device_ownership_from_security_context = true' /var/snap/microk8s/current/args/containerd-template.toml
sudo mkdir -p /var/snap/microk8s/current/args/certs.d/registry.internal.local:32000
sudo cp ../hosts.toml /var/snap/microk8s/current/args/certs.d/registry.internal.local:32000
#sudo cp ../daemon.json /etc/docker/
#sudo cp ../daemon.json /var/snap/docker/current/config/
NODENAME=$(cat /etc/hostname)
NETWORK_INTERFACE=enp2s0
sudo microk8s kubectl annotate node $NODENAME "ovn.kubernetes.io/tunnel_interface=$NETWORK_INTERFACE"
sudo microk8s enable kube-ovn --force

sudo microk8s status --wait-ready
sudo microk8s inspect

#sudo microk8s kubectl label node ubuntu node-role.kubernetes.io/worker=""