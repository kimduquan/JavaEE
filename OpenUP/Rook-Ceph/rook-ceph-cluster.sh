helm repo add rook https://charts.rook.io/release
helm upgrade --install --create-namespace --namespace rook-ceph rook-ceph-cluster rook/rook-ceph-cluster --wait --timeout 20m -f values-rook-ceph-cluster.yaml
kubectl wait -n rook-ceph cephcluster rook-ceph --for condition=Ready --timeout=1200s
kubectl wait -n rook-ceph --for=condition=Ready pod -l app=rook-ceph-osd --timeout=10m