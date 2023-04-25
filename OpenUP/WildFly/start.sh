kubectl apply -f ./wildfly.yaml
kubectl wait deployment --for condition=available wildfly
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=wildfly
kubectl port-forward svc/wildfly-internal 9990:9990 &
sudo kubectl port-forward svc/wildfly 443 &