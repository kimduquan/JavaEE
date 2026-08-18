helm repo add rook https://charts.rook.io/release
helm upgrade --install --create-namespace --namespace rook-ceph rook-ceph rook/rook-ceph --wait --timeout 20m -f values-rook-ceph.yaml