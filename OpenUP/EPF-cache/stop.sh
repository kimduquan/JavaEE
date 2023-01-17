#kubectl delete -f target/kubernetes/kubernetes.yml
#kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-cache
#kubectl wait --for=delete -f target/kubernetes/kubernetes.yml
kubectl delete deployment -l app.kubernetes.io/name=epf-cache
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-cache
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-cache