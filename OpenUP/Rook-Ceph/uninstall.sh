sudo rm -rf /var/lib/rook-ceph
sudo rm -rf /var/lib/rook/
DISK="/dev/sde"
sudo sgdisk --zap-all $DISK
sudo dd if=/dev/zero of="$DISK" bs=1K count=200 oflag=direct,dsync seek=0
sudo dd if=/dev/zero of="$DISK" bs=1K count=200 oflag=direct,dsync seek=$((1 * 1024**2))
sudo dd if=/dev/zero of="$DISK" bs=1K count=200 oflag=direct,dsync seek=$((10 * 1024**2))
sudo dd if=/dev/zero of="$DISK" bs=1K count=200 oflag=direct,dsync seek=$((100 * 1024**2))
sudo dd if=/dev/zero of="$DISK" bs=1K count=200 oflag=direct,dsync seek=$((1000 * 1024**2))
sudo blkdiscard $DISK
sudo partprobe $DISK