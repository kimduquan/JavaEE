kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available --timeout=60s epf-query
kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=epf-query