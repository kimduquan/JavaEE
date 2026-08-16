. ../../env.sh
helm repo add kedacore https://kedacore.github.io/charts
helm repo update
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --create-namespace --namespace keda --wait --timeout 20m keda kedacore/keda -f values-keda.yaml