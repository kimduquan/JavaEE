kubectl delete hpa epf-registry
#kubectl delete deployment -l app.kubernetes.io/name=epf-registry
#kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-registry
#kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-registry
helm uninstall epf-registry --wait