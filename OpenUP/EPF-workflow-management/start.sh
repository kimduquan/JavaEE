kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-workflow-management
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-workflow-management