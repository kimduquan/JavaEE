kubectl delete hpa epf-config
kubectl delete deployment -l app.kubernetes.io/name=epf-config
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-config
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-config