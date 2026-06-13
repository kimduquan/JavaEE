. ./env.sh
kubectl port-forward svc/wildfly 9990:9990 &
cd EPF-webapp-v2
mvn wildfly:deploy