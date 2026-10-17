kubectl create -f nfs.yaml
kubectl wait -n rook-ceph deployment rook-ceph-nfs-ceph-nfs-a --for condition=Available
kubectl wait -n rook-ceph --for=condition=Ready pod -l app=rook-ceph-nfs