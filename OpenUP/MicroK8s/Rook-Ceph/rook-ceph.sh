. ../../env.sh ../../
. ../../Rook-Ceph/env.sh
../../registry.sh quay.io "$TOOLBOX_IMAGE"
../../registry.sh quay.io "ceph/ceph:$CEPH_IMAGE_TAG"
../../registry.sh quay.io "cephcsi/ceph-csi-operator:$CEPH_CSI_OPERATOR_IMAGE_TAG"
../../registry.sh quay.io "rook/ceph:$ROOK_CEPH_IMAGE_TAG"
../../registry.sh quay.io "cephcsi/cephcsi:$CEPH_CSI_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-node-driver-registrar:$CSI_REGISTRAR_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-provisioner:$CSI_PROVISIONER_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-resizer:$CSI_RESIZER_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-attacher:$CSI_ATTACHER_IMAGE_TAG"
../../registry.sh registry.k8s.io "sig-storage/csi-snapshotter:$CSI_SNAPSHOTTER_IMAGE_TAG"
$helm repo add rook https://charts.rook.io/release
$helm upgrade --install --create-namespace --namespace rook-ceph --wait --timeout 40m rook-ceph rook/rook-ceph  --version $ROOK_CEPH_HELM_VERSION -f values.yaml \
	--set "toolbox.image=$EPF_CLUSTER_IMAGE_REGISTRY/$TOOLBOX_IMAGE" \
	--set "cephImage.repository=$EPF_CLUSTER_IMAGE_REGISTRY/ceph/ceph" \
	--set "cephImage.tag=$CEPH_IMAGE_TAG" \
	--set "ceph-csi-operator.controllerManager.manager.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cephcsi/ceph-csi-operator" \
	--set "ceph-csi-operator.controllerManager.manager.image.tag=$CEPH_CSI_OPERATOR_IMAGE_TAG" \
	--set "image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/rook/ceph" \
	--set "image.tag=$ROOK_CEPH_IMAGE_TAG" \
	--set "csi.cephcsi.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cephcsi/cephcsi" \
	--set "csi.cephcsi.tag=$CEPH_CSI_IMAGE_TAG" \
	--set "csi.registrar.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-node-driver-registrar" \
	--set "csi.registrar.tag=$CSI_REGISTRAR_IMAGE_TAG" \
	--set "csi.provisioner.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-provisioner" \
	--set "csi.provisioner.tag=$CSI_PROVISIONER_IMAGE_TAG" \
	--set "csi.resizer.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-resizer" \
	--set "csi.resizer.tag=$CSI_RESIZER_IMAGE_TAG" \
	--set "csi.attacher.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-attacher" \
	--set "csi.attacher.tag=$CSI_ATTACHER_IMAGE_TAG" \
	--set "csi.snapshotter.repository=$EPF_CLUSTER_IMAGE_REGISTRY/sig-storage/csi-snapshotter" \
	--set "csi.snapshotter.tag=$CSI_SNAPSHOTTER_IMAGE_TAG"
$helm upgrade --install --create-namespace --namespace rook-ceph --wait --timeout 40m rook-ceph-cluster --version $ROOK_CEPH_CLUSTER_HELM_VERSION --set operatorNamespace=rook-ceph rook/rook-ceph-cluster -f ../../Rook-Ceph/values-external.yaml
$kubectl wait -n rook-ceph cephcluster rook-ceph --for condition=Connecting=True --timeout=1200s
$helm repo add ceph-csi-operator https://ceph.github.io/ceph-csi-operator
$helm upgrade --install ceph-csi-drivers --namespace rook-ceph --wait --timeout 30m ceph-csi-operator/ceph-csi-drivers -f ../../Rook-Ceph/csi-values.yaml
$kubectl wait -n rook-ceph -l app=rook-ceph.rbd.csi.ceph.com-ctrlplugin pod --for condition=Ready --timeout=1200s
$kubectl wait -n rook-ceph -l app=rook-ceph.cephfs.csi.ceph.com-ctrlplugin pod --for condition=Ready --timeout=1200s

. ../../Rook-Ceph/export-env.sh

../../Rook-Ceph/import-external-cluster.sh

$kubectl wait -n rook-ceph cephcluster rook-ceph --for condition=Connected=True --timeout=1200s
