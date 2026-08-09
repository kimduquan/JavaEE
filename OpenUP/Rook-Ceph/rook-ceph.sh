helm repo add rook https://charts.rook.io/release
helm upgrade --install --create-namespace --namespace rook-ceph rook-ceph rook/rook-ceph --wait -f values-rook-ceph.yaml