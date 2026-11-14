sudo snap info k8s
sudo snap install k8s --classic --channel=1.35-classic/stable
sudo k8s bootstrap
sudo k8s status --wait-ready