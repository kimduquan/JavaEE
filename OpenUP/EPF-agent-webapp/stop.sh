kubectl delete hpa epf-agent-webapp
kubectl delete deployment -l app.kubernetes.io/name=epf-agent-webapp
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-agent-webapp
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-agent-webapp
kubectl delete secret nextauth