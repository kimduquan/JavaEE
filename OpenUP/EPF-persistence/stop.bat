kubectl delete hpa epf-persistence
helm uninstall epf-persistence --wait
::kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-persistence
::kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-persistence