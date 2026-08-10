helm repo add kedacore https://kedacore.github.io/charts
helm repo update
helm upgrade --install --create-namespace --namespace keda --wait keda kedacore/keda -f values-keda.yaml