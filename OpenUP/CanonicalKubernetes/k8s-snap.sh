sudo snap info k8s
sudo snap install k8s --classic --channel=1.35-classic/stable
sudo k8s set load-balancer.cidrs=192.168.1.220-192.168.1.234
sudo k8s set load-balancer.l2-interfaces=eth0
sudo k8s bootstrap
sudo k8s status --wait-ready --timeout 3m
sudo k8s enable load-balancer
#sudo k8s helm upgrade ck-network cilium/cilium --version 1.18.4 --namespace kube-system --reuse-values --wait -f ck-network-all-values.yaml -f ck-network-values.yaml