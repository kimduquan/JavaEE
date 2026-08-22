cd EPF-cluster
./uninstall.sh
cd ../

helm uninstall cluster-autoscaler

./stop.sh

sudo snap remove kubectl --purge
sudo snap remove microk8s --purge

sudo umount /var/lib/kubelet
sudo rm -rf /var/lib/kubelet
sudo rm -rf /var/lib/kubevirt-node-labeller
sudo rm -rf /var/run/kubevirt
sudo rm -rf /var/run/kubevirt-libvirt-runtimes
sudo rm -rf /var/run/kubevirt-private

cd Rook-Ceph
./uninstall.sh
cd ../