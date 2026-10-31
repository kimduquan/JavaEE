grep -qxF "kernel.panic = 10" /etc/sysctl.conf || echo "kernel.panic = 10" | sudo tee -a /etc/sysctl.conf
grep -qxF "vm.overcommit_memory = 1" /etc/sysctl.conf || echo "vm.overcommit_memory = 1" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p
sudo snap install microk8s --classic
sudo microk8s status --wait-ready
sudo microk8s inspect
# https://github.com/kubevirt/containerized-data-importer/blob/main/doc/block_cri_ownership_config.md
sudo sed -i '/\[plugins\."io\.containerd\.grpc\.v1\.cri"\]/a \  device_ownership_from_security_context = true' /var/snap/microk8s/current/args/containerd-template.toml
#sudo mkdir -p /var/snap/microk8s/current/args/containerd.d
#sudo cp capk-cdi.toml /var/snap/microk8s/current/args/containerd.d
#sudo mkdir -p /var/snap/microk8s/current/args/certs.d/172.23.225.247:32000
#sudo cp hosts.toml /var/snap/microk8s/current/args/certs.d/172.23.225.247:32000
#./stop.sh
#sudo unlink /var/lib/kubelet
#sudo umount /var/lib/kubelet
#./start.sh
sudo snap install kubectl --classic
NODENAME=$(cat /etc/hostname)
NETWORK_INTERFACE=eth0
sudo microk8s kubectl annotate node $NODENAME "ovn.kubernetes.io/tunnel_interface=$NETWORK_INTERFACE"
sudo microk8s enable kube-ovn --force
sudo microk8s status --wait-ready
sudo microk8s inspect

#sudo microk8s kubectl label node $NODENAME node-role.kubernetes.io/control-plane=""
#sudo microk8s kubectl label node $NODENAME nvidia.com/gpu.present=true
#sudo microk8s config > ~/.kube/config