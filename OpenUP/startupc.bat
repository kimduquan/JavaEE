kubectl apply -f ./postgresql.yaml
kubectl wait deployment --for condition=available postgresql
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=postgresql

kubectl apply -f ./zookeeper.yaml
kubectl wait deployment --for condition=available zookeeper
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=zookeeper

kubectl apply -f ./kafka.yaml
kubectl wait deployment --for condition=available kafka
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=kafka

cd EPF-transaction-internal
kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-transaction-internal
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-transaction-internal
cd ../

cd EPF-transaction
kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-transaction
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-transaction
cd ../

cd EPF-persistence
kubectl apply -f target/kubernetes/kubernetes.yml
kubectl wait deployment --for condition=available epf-persistence
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-persistence
cd ../

cd EPF-tests
kubectl apply -f ./openup.yaml
kubectl wait pod --for condition=ready -l name=openup --timeout=300s
cd ../

cd EPF-gateway
kubectl apply -f ./epf-gateway.yaml
kubectl wait deployment --for condition=available epf-gateway --timeout=300s
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-gateway --timeout=300s
cd ../

cd EPF-webapp
kubectl apply -f ./epf-webapp.yaml
kubectl wait deployment --for condition=available epf-webapp
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-webapp
cd ../

start kubectl port-forward svc/epf-gateway-internal 8080:8080
start kubectl port-forward svc/epf-webapp-internal 9990:9990