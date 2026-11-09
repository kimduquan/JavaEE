. ../env.sh ../
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install prometheus prometheus-community/prometheus --version 29.45.1 --wait --namespace prometheus --create-namespace --wait -f values-prometheus.yaml