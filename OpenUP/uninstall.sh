cd EPF-cluster
./uninstall.sh
cd ../

helm uninstall cluster-autoscaler

./stop.sh

sudo snap remove kubectl --purge
sudo snap remove microk8s --purge

sudo umount /var/lib/kubelet
sudo rm -rf /var/lib/kubelet

sudo rm -rf /var/lib/rook-ceph
sudo rm -rf /var/lib/rook/
sudo wipefs -af /dev/sde