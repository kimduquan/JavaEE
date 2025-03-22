kubectl apply -f ./epf-auth.yml
kubectl wait deployment --for condition=available epf-auth --timeout=300s
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-auth --timeout=300s