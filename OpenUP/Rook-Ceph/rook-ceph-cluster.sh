helm repo add rook https://charts.rook.io/release
helm upgrade --install --create-namespace --namespace rook-ceph rook-ceph-cluster rook/rook-ceph-cluster --wait -f values-rook-ceph-cluster.yaml