#kubectl apply -f target/kubernetes/kubernetes.yml
#kubectl wait deployment --for condition=available epf-registry
#kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-registry
helm install epf-registry target/helm/kubernetes/epf-registry --wait
kubectl autoscale deployment epf-registry --max 3