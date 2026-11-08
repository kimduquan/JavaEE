. ../../env.sh ../../
KEDA_VERSION=2.21.0
../../registry.sh ghcr.io "kedacore/keda:$KEDA_VERSION"
../../registry.sh ghcr.io "kedacore/keda-metrics-apiserver:$KEDA_VERSION"
../../registry.sh ghcr.io "kedacore/keda-admission-webhooks:$KEDA_VERSION"
../../registry.sh ghcr.io "kedacore/http-add-on-operator:latest"
../../registry.sh ghcr.io "kedacore/http-add-on-interceptor:latest"
../../registry.sh ghcr.io "kedacore/http-add-on-scaler:latest"
helm repo add kedacore https://kedacore.github.io/charts
helm repo update
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --create-namespace --namespace keda --version "$KEDA_VERSION" --wait --timeout 20m keda kedacore/keda -f values-keda.yaml \
	--set "global.image.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.keda.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.metricsApiServer.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.webhooks.registry=$EPF_CLUSTER_IMAGE_REGISTRY"
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --namespace keda keda-add-ons-http kedacore/keda-add-ons-http --version "0.16.0" --wait -f values-keda-add-ons-http.yaml \
	--set "images.operator=$EPF_CLUSTER_IMAGE_REGISTRY/kedacore/http-add-on-operator" \
	--set "images.interceptor=$EPF_CLUSTER_IMAGE_REGISTRY/kedacore/http-add-on-interceptor" \
	--set "images.scaler=$EPF_CLUSTER_IMAGE_REGISTRY/kedacore/http-add-on-scaler"