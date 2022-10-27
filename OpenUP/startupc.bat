kubectl apply -f ./zookeeper.yaml
kubectl get deployment zookeeper
kubectl wait deployment --for condition=available zookeeper
kubectl get deployment zookeeper
kubectl get pod -l app.kubernetes.io/name=zookeeper
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=zookeeper
kubectl get pod -l app.kubernetes.io/name=zookeeper

kubectl apply -f ./kafka.yaml
kubectl get deployment kafka
kubectl wait deployment --for condition=available kafka
kubectl get deployment kafka
kubectl get pod -l app.kubernetes.io/name=kafka
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=kafka
kubectl get pod -l app.kubernetes.io/name=kafka

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