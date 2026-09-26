. ../../env.sh
KEDA_VERSION=2.21.0
../../registry.sh ghcr.io "kedacore/keda:$KEDA_VERSION"
../../registry.sh ghcr.io "kedacore/keda-metrics-apiserver:$KEDA_VERSION"
../../registry.sh ghcr.io "kedacore/keda-admission-webhooks:$KEDA_VERSION"
helm repo add kedacore https://kedacore.github.io/charts
helm repo update
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --create-namespace --namespace keda --version "$KEDA_VERSION" --wait --timeout 20m keda kedacore/keda -f values-keda.yaml \
	--set "global.image.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.keda.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.metricsApiServer.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.webhooks.registry=$EPF_CLUSTER_IMAGE_REGISTRY"