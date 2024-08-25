kubectl apply -f ./scylladb.yaml
kubectl wait deployment --for condition=available scylla
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=scylla