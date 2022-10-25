kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-transaction-internal
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-transaction-internal