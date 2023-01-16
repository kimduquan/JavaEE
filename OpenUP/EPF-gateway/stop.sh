kubectl delete -f ./epf-gateway.yaml
kubectl get pod -l app.kubernetes.io/name=epf-gateway
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-gateway
kubectl wait --for=delete -f ./epf-gateway.yaml