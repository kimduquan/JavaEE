kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-mcp-gateway
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-mcp-gateway
kubectl autoscale deployment epf-mcp-gateway --max 2