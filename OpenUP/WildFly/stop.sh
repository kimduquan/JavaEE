kubectl delete -f ./wildfly.yaml
kubectl get pod -l app.kubernetes.io/name=wildfly
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=wildfly
kubectl wait --for=delete -f ./wildfly.yaml