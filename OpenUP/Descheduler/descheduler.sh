helm repo add descheduler https://kubernetes-sigs.github.io/descheduler/
. ../env.sh ../
VERSION="0.37.0"
IMAGE_TAG="v$VERSION"
../registry.sh registry.k8s.io "descheduler/descheduler:$IMAGE_TAG"
helm upgrade --install --wait descheduler --namespace kube-system descheduler/descheduler --version "$VERSION" -f values-descheduler.yaml \
	--set "image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/descheduler/descheduler" \
	--set "image.tag=$IMAGE_TAG"