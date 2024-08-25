kubectl delete deployment -l app.kubernetes.io/name=epf-workflow-management
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-workflow-management
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-workflow-management