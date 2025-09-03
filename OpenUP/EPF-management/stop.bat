kubectl delete deployment -l app.kubernetes.io/name=epf-management
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-management
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-management