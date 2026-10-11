NODE_IP=$(kubectl get node ubuntu -o wide --no-headers | awk '{print $6}')
kubectl label node ubuntu node-role.kubernetes.io/worker=""
kubectl annotate node ubuntu ovn.kubernetes.io/tunnel_interface=enp2s0 --overwrite