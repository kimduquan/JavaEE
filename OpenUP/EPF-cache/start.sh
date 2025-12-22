#kubectl apply -f target/kubernetes/kubernetes.yml
#kubectl wait deployment --for condition=available --timeout=60s epf-cache
#kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=epf-cache
helm install epf-cache target/helm/kubernetes/epf-cache --wait
kubectl autoscale deployment epf-cache --max 3