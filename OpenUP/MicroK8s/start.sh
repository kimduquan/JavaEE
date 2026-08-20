#sudo rm -r -f /var/lib/kubelet/
#sudo mkdir /var/lib/kubelet
sudo mount --rbind /var/snap/microk8s/common/var/lib/kubelet /var/lib/kubelet
sudo mount --make-rshared /var/lib/kubelet
#sudo snap start microk8s
#sudo microk8s start
#sudo microk8s inspect