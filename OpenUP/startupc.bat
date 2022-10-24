call kubectl apply -f ./zookeeper.yaml
call kubectl wait deployment --for condition=available zookeeper
call kubectl apply -f ./kafka.yaml
call kubectl wait deployment --for condition=available kafka
cd EPF-transaction-internal
call kubectl apply -f target/kubernetes/kubernetes.yml
call kubectl wait deployment --for condition=available epf-transaction-internal
cd ../
cd EPF-transaction
call kubectl apply -f target/kubernetes/kubernetes.yml
call kubectl wait deployment --for condition=available epf-transaction
cd ../
cd EPF-persistence
call kubectl apply -f target/kubernetes/kubernetes.yml
call kubectl wait deployment --for condition=available epf-persistence
cd ../
cd EPF-tests
call kubectl apply -f ./openup.yaml
call kubectl wait deployment --for condition=available openup
cd ../
cd EPF-gateway
call kubectl apply -f ./epf-gateway.yaml
call kubectl wait deployment --for condition=available epf-gateway
cd ../
cd EPF-webapp
call kubectl apply -f ./epf-webapp.yaml
call kubectl wait deployment --for condition=available epf-webapp
cd ../
start kubectl port-forward svc/epf-gateway-internal 8080:8080
start kubectl port-forward svc/epf-webapp-internal 9990:9990