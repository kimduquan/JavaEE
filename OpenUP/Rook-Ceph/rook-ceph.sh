. ../env.sh ../
. env.sh
$helm repo add rook https://charts.rook.io/release
$helm upgrade --install --create-namespace --namespace rook-ceph rook-ceph rook/rook-ceph --version $ROOK_CEPH_HELM_VERSION --wait --timeout 20m -f values-rook-ceph.yaml