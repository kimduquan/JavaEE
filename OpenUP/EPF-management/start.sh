kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-management
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-management
kubectl autoscale deployment epf-management --max 3