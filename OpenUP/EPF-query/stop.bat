kubectl delete hpa epf-query
helm uninstall epf-query --wait
::kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-query
::kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-query