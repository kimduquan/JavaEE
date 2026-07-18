kubectl delete hpa epf-agent
kubectl delete deployment -l app.kubernetes.io/name=epf-agent
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-agent
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-agent