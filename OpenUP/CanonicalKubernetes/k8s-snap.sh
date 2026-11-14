sudo snap info k8s
sudo snap install k8s --classic --channel=1.35-classic/stable
sudo k8s bootstrap
sudo k8s status --wait-ready --timeout 3m
sudo k8s enable load-balancer
sudo k8s set load-balancer.cidrs=192.168.1.220-192.168.1.234
sudo k8s set load-balancer.l2-interfaces=eth0