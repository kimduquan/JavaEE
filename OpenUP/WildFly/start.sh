kubectl apply -f ./wildfly.yaml
kubectl wait deployment --for condition=available wildfly
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=wildfly