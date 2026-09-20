. ../../env.sh
TOOLBOX_IMAGE=ceph/ceph:v20.2.2
CEPH_IMAGE_TAG=v20.2.2
ROOK_CEPH_IMAGE_TAG=v1.20.7
CEPH_CSI_OPERATOR_IMAGE_TAG=v1.0.4
CEPH_CSI_IMAGE_TAG=v3.17.1
CSI_PROVISIONER_IMAGE_TAG=v6.2.0
CSI_RESIZER_IMAGE_TAG=v2.1.0
CSI_ATTACHER_IMAGE_TAG=v4.12.0
CSI_SNAPSHOTTER_IMAGE_TAG=v8.5.0
../../registry.sh quay.io "$TOOLBOX_IMAGE"
../../registry.sh quay.io "ceph/ceph:$CEPH_IMAGE_TAG"
../../registry.sh quay.io "cephcsi/ceph-csi-operator:$CEPH_CSI_OPERATOR_IMAGE_TAG"
../../registry.sh docker.io "rook/ceph:$ROOK_CEPH_IMAGE_TAG"
../../registry.sh quay.io "cephcsi/cephcsi:$CEPH_CSI_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-provisioner:$CSI_PROVISIONER_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-resizer:$CSI_RESIZER_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-attacher:$CSI_ATTACHER_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-snapshotter:$CSI_SNAPSHOTTER_IMAGE_TAG"
helm repo add rook https://charts.rook.io/release
helm --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" upgrade --install --create-namespace --namespace rook-ceph --wait --timeout 40m rook-ceph rook/rook-ceph -f values.yaml \
	--set "toolbox.image=$EPF_CLUSTER_IMAGE_REGISTRY/$TOOLBOX_IMAGE" \
	--set "cephImage.repository=$EPF_CLUSTER_IMAGE_REGISTRY/ceph/ceph" \
	--set "cephImage.tag=$CEPH_IMAGE_TAG" \
	--set "ceph-csi-operator.controllerManager.manager.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cephcsi/ceph-csi-operator" \
	--set "image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/rook/ceph" \
	--set "csi.cephcsi.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cephcsi/cephcsi" \
	--set "csi.provisioner.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-provisioner" \
	--set "csi.resizer.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-resizer" \
	--set "csi.attacher.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-attacher" \
	--set "csi.snapshotter.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-snapshotter"
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --create-namespace --namespace rook-ceph --wait --timeout 40m rook-ceph-cluster --set operatorNamespace=rook-ceph rook/rook-ceph-cluster -f values-external.yaml
kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} wait -n rook-ceph cephcluster rook-ceph --for condition=Connecting=True --timeout=1200s
helm repo add ceph-csi-operator https://ceph.github.io/ceph-csi-operator
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install ceph-csi-drivers --namespace rook-ceph --wait --timeout 30m ceph-csi-operator/ceph-csi-drivers -f csi-values.yaml
kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} wait -n rook-ceph -l app=rook-ceph.rbd.csi.ceph.com-ctrlplugin pod --for condition=Ready --timeout=1200s