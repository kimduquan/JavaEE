kubectl delete hpa epf-agent
kubectl delete -f kubernetes.yml
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-agent
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-agent