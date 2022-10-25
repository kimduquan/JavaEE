kubectl apply -f ./epf-webapp.yaml
kubectl wait deployment --for condition=available epf-webapp
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-webapp
start kubectl port-forward svc/epf-webapp-internal 9990:9990