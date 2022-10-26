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
call start.bat
cd ../

cd EPF-transaction
call start.bat
cd ../

cd EPF-persistence
call start.bat
cd ../

cd EPF-tests
call start.bat
cd ../

cd EPF-gateway
call start.bat
cd ../

cd EPF-webapp
call start.bat
cd ../

start kubectl port-forward svc/epf-gateway-internal 8080:8080
start kubectl port-forward svc/epf-webapp-internal 9990:9990