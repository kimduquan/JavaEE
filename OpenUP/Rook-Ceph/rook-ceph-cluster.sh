. ../env.sh ../
. env.sh
$helm repo add rook https://charts.rook.io/release
$helm upgrade --install --create-namespace --namespace rook-ceph rook-ceph-cluster rook/rook-ceph-cluster --version $ROOK_CEPH_CLUSTER_HELM_VERSION --wait --timeout 20m --set cephObjectStores=null -f values-rook-ceph-cluster.yaml
$kubectl wait -n rook-ceph cephcluster rook-ceph --for condition=Ready --timeout=1200s
$kubectl wait -n rook-ceph --for=condition=Ready pod -l app=rook-ceph-osd --timeout=10m