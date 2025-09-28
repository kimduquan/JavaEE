kubectl delete hpa epf-transaction
kubectl delete deployment -l app.kubernetes.io/name=epf-transaction
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-transaction
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-transaction