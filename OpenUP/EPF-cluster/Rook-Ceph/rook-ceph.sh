. ../../env.sh
helm repo add rook https://charts.rook.io/release
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --create-namespace --namespace rook-ceph --wait --timeout 20m rook-ceph rook/rook-ceph -f values.yaml
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --create-namespace --namespace rook-ceph --wait --timeout 20m rook-ceph-cluster --set operatorNamespace=rook-ceph rook/rook-ceph-cluster -f values-external.yaml
kubectl wait -n rook-ceph cephcluster rook-ceph --for condition=Connecting=True --timeout=1200s
helm repo add ceph-csi-operator https://ceph.github.io/ceph-csi-operator
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install ceph-csi-drivers --namespace rook-ceph --wait --timeout 30m ceph-csi-operator/ceph-csi-drivers -f csi-values.yaml