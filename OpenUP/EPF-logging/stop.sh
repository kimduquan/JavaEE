#kubectl delete deployment -l app.kubernetes.io/name=epf-logging
#kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-logging
#kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-logging
helm uninstall epf-logging --wait