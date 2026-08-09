. ../env.sh
export KUBECONFIG=$
helm repo add rook https://charts.rook.io/release
helm install --create-namespace --namespace rook-ceph rook-ceph rook/rook-ceph -f values.yaml
helm install --create-namespace --namespace rook-ceph rook-ceph-cluster --set operatorNamespace=rook-ceph rook/rook-ceph-cluster -f values-external.yaml
helm repo add ceph-csi-operator https://ceph.github.io/ceph-csi-operator
helm install ceph-csi-drivers --namespace rook-ceph ceph-csi-operator/ceph-csi-drivers -f https://raw.githubusercontent.com/rook/rook/master/deploy/charts/ceph-csi-drivers/values.yaml