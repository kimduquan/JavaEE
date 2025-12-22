kubectl delete hpa epf-messaging
#kubectl delete deployment -l app.kubernetes.io/name=epf-messaging
#kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-messaging
#kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-messaging
helm uninstall epf-messaging --wait