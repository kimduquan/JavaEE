kubectl delete -f ./target/kubernetes/kubernetes.yml
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-gateway
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-gateway