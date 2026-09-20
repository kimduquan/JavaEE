. ../../env.sh
TOOLBOX_IMAGE=ceph/ceph:v20.2.2
CEPH_IMAGE_TAG=v20.2.2
../../registry.sh quay.io "$TOOLBOX_IMAGE"
../../registry.sh quay.io "ceph/ceph:$CEPH_IMAGE_TAG"
helm repo add rook https://charts.rook.io/release
helm --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" upgrade --install --create-namespace --namespace rook-ceph --wait --timeout 40m rook-ceph rook/rook-ceph -f values.yaml \
	--set "toolbox.image=$EPF_CLUSTER_IMAGE_REGISTRY/$TOOLBOX_IMAGE" \
	--set "cephImage.repository=$EPF_CLUSTER_IMAGE_REGISTRY/ceph/ceph" \
	--set "cephImage.tag=$CEPH_IMAGE_TAG"
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --create-namespace --namespace rook-ceph --wait --timeout 40m rook-ceph-cluster --set operatorNamespace=rook-ceph rook/rook-ceph-cluster -f values-external.yaml
kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} wait -n rook-ceph cephcluster rook-ceph --for condition=Connecting=True --timeout=1200s
helm repo add ceph-csi-operator https://ceph.github.io/ceph-csi-operator
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install ceph-csi-drivers --namespace rook-ceph --wait --timeout 30m ceph-csi-operator/ceph-csi-drivers -f csi-values.yaml
kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} wait -n rook-ceph -l app=rook-ceph.rbd.csi.ceph.com-ctrlplugin pod --for condition=Ready --timeout=1200s