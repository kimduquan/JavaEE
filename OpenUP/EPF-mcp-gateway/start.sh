#kubectl apply -f target/kubernetes/kubernetes.yml
#kubectl wait deployment --for condition=available epf-mcp-gateway
#kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-mcp-gateway
helm install epf-mcp-gateway target/helm/kubernetes/epf-mcp-gateway --wait
kubectl autoscale deployment epf-mcp-gateway --max 2