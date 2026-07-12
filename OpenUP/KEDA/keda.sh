helm repo add kedacore https://kedacore.github.io/charts  
helm repo update
helm upgrade --install --wait keda kedacore/keda
kubectl apply -f keda-2.20.1-crds.yaml