kubectl delete -f target/kubernetes/kubernetes.yml
kubectl get pod -l app.kubernetes.io/name=epf-cache
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-cache
kubectl wait --for=delete -f target/kubernetes/kubernetes.yml