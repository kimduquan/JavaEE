kubectl delete -f ./epf-auth.yml
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-auth
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-auth