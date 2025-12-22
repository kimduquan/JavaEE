#kubectl apply -f target/kubernetes/kubernetes.yml
#kubectl wait deployment --for condition=available epf-net
#kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-net
helm install epf-net target/helm/kubernetes/epf-net --wait
kubectl autoscale deployment epf-net --max 3