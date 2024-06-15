kubectl delete deployment -l app.kubernetes.io/name=epf-lang
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-lang
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-lang