kubectl apply -f ./epf-gateway.yaml
kubectl wait deployment --for condition=available epf-gateway --timeout=300s
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-gateway --timeout=300s
kubectl port-forward svc/epf-gateway-internal 8080:8080 &