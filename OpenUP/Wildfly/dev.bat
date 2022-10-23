docker build -t wildfly:1.0.0 ./
kubectl delete -f wildfly.yml
kubectl apply -f wildfly.yml