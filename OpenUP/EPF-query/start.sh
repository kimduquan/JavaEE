kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-query
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-query