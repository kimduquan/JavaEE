kubectl delete hpa epf-cache
#kubectl delete deployment -l app.kubernetes.io/name=epf-cache
#kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-cache
#kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-cache
helm uninstall epf-cache --wait