kubectl delete hpa epf-mcp-gateway
#kubectl delete deployment -l app.kubernetes.io/name=epf-mcp-gateway
#kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-mcp-gateway
#kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-mcp-gateway
helm uninstall epf-mcp-gateway --wait