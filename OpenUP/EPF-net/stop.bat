kubectl delete -f target/kubernetes/kubernetes.yml
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-net
kubectl wait --for=delete -f target/kubernetes/kubernetes.yml