kubectl apply -f kubernetes.yml
kubectl wait deployment --for condition=available epf-agent
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-agent
kubectl autoscale deployment epf-agent --max 1