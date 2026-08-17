sudo mkfs.ext4 /dev/vdb
sudo systemctl stop kubelet containerd
sudo rm -rf /var/lib/containerd/*
sudo mount /dev/vdb /var/lib/containerd
sudo mount --make-rshared /var/lib/containerd
sudo systemctl start containerd kubelet