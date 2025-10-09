helm install epf-query target/helm/kubernetes/epf-query --wait
#kubectl wait deployment --for condition=available --timeout=60s epf-query
#kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=epf-query
kubectl autoscale deployment epf-query --max 2