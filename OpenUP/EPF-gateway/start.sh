kubectl apply -f ./target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-gateway --timeout=300s
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-gateway --timeout=300s