helm repo add ceph-csi-operator https://ceph.github.io/ceph-csi-operator
helm upgrade --install ceph-csi-drivers --namespace rook-ceph --wait --timeout 30m ceph-csi-operator/ceph-csi-drivers -f values.yaml
kubectl wait -n rook-ceph -l app=rook-ceph.rbd.csi.ceph.com-ctrlplugin pod --for condition=Ready --timeout=1200s