#kubectl apply -f target/kubernetes/kubernetes.yml
#kubectl wait deployment --for condition=available epf-management
#kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-management
helm install epf-management target/helm/kubernetes/epf-management --wait
kubectl autoscale deployment epf-management --max 3