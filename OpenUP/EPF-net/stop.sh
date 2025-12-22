kubectl delete hpa epf-net
#kubectl delete deployment -l app.kubernetes.io/name=epf-net
#kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-net
#kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-net
helm uninstall epf-net --wait