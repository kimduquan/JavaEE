. ../env.sh ../
. env.sh
# https://github.com/ceph/ceph-csi-operator/blob/v1.1.0/deploy/charts/ceph-csi-drivers/values.yaml
$helm repo add ceph-csi-operator https://ceph.github.io/ceph-csi-operator
$helm upgrade --install ceph-csi-drivers --namespace rook-ceph --wait --timeout 40m ceph-csi-operator/ceph-csi-drivers -f values.yaml
$kubectl wait -n rook-ceph -l app=rook-ceph.rbd.csi.ceph.com-ctrlplugin pod --for condition=Ready --timeout=1200s
$kubectl wait -n rook-ceph -l app=rook-ceph.rbd.csi.ceph.com-nodeplugin pod --for condition=Ready --timeout=1200s