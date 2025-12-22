#kubectl apply -f target/kubernetes/kubernetes.yml
#kubectl wait deployment --for condition=available epf-config
#kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-config
helm install epf-config target/helm/kubernetes/epf-config --wait
kubectl autoscale deployment epf-config --max 3