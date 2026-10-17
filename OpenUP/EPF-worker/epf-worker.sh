NODE_IP=$(kubectl get node ubuntu -o wide --no-headers | awk '{print $6}')
kubectl label node ubuntu node-role.kubernetes.io/worker=""
#sudo microk8s kubectl annotate node ubuntu ovn.kubernetes.io/tunnel_interface=enp2s0 --overwrite
#sudo sed -i '/\[plugins\."io\.containerd\.grpc\.v1\.cri"\]/a \  device_ownership_from_security_context = true' /var/snap/microk8s/current/args/containerd-template.toml