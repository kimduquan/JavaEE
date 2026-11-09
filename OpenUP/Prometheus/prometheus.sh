. ../env.sh ../
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
../registry.sh quay.io "prometheus/prometheus:v3.16.0"
../registry.sh quay.io "prometheus/pushgateway:v1.11.4"
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install prometheus prometheus-community/prometheus --version 29.45.1 --wait --namespace prometheus --create-namespace --wait -f values-prometheus.yaml \
	--set "server.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/prometheus/prometheus"