. ../../env.sh ../../
KEDA_HTTP_ADD_ON_VERSION=0.16.0
../../registry.sh ghcr.io "kedacore/http-add-on-operator:$KEDA_HTTP_ADD_ON_VERSION"
../../registry.sh ghcr.io "kedacore/http-add-on-interceptor:$KEDA_HTTP_ADD_ON_VERSION"
../../registry.sh ghcr.io "kedacore/http-add-on-scaler:$KEDA_HTTP_ADD_ON_VERSION"
helm repo add kedacore https://kedacore.github.io/charts
helm repo update
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --namespace keda keda-add-ons-http kedacore/keda-add-ons-http --version "$KEDA_HTTP_ADD_ON_VERSION" --wait -f values-keda-add-ons-http.yaml \
	--set "images.operator=$EPF_CLUSTER_IMAGE_REGISTRY/kedacore/http-add-on-operator" \
	--set "images.interceptor=$EPF_CLUSTER_IMAGE_REGISTRY/kedacore/http-add-on-interceptor" \
	--set "images.scaler=$EPF_CLUSTER_IMAGE_REGISTRY/kedacore/http-add-on-scaler"