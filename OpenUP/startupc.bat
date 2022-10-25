call kubectl apply -f ./postgresql.yaml
call kubectl wait deployment --for condition=available postgresql
call kubectl wait pod --for condition=ready -l app.kubernetes.io/name=postgresql

call kubectl apply -f ./zookeeper.yaml
call kubectl wait deployment --for condition=available zookeeper
call kubectl wait pod --for condition=ready -l app.kubernetes.io/name=zookeeper

call kubectl apply -f ./kafka.yaml
call kubectl wait deployment --for condition=available kafka
call kubectl wait pod --for condition=ready -l app.kubernetes.io/name=kafka

cd EPF-transaction-internal
call kubectl apply -f target/kubernetes/kubernetes.yml
call kubectl wait deployment --for condition=available epf-transaction-internal
call kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-transaction-internal
cd ../

cd EPF-transaction
call kubectl apply -f target/kubernetes/kubernetes.yml
call kubectl wait deployment --for condition=available epf-transaction
call kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-transaction
cd ../

cd EPF-persistence
call kubectl apply -f target/kubernetes/kubernetes.yml
call kubectl wait deployment --for condition=available epf-persistence
call kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-persistence
cd ../

cd EPF-tests
call kubectl apply -f ./openup.yaml
call kubectl wait pod --for condition=ready -l name=openup --timeout=300s
cd ../

cd EPF-gateway
call kubectl apply -f ./epf-gateway.yaml
call kubectl wait deployment --for condition=available epf-gateway --timeout=300s
call kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-gateway --timeout=300s
cd ../

cd EPF-webapp
call kubectl apply -f ./epf-webapp.yaml
call kubectl wait deployment --for condition=available epf-webapp
call kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-webapp
cd ../

start kubectl port-forward svc/epf-gateway-internal 8080:8080
start kubectl port-forward svc/epf-webapp-internal 9990:9990