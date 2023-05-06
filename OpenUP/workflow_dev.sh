. ./env.sh
./shutdownc.sh
cd EPF-config
./start.sh
cd ../
cd EPF-messaging
./start.sh
cd ../
cd EPF-registry
./start.sh
cd ../
cd EPF-gateway
./start.sh
cd ../
./webapp_startupc.sh
kubectl port-forward svc/wildfly-internal 9990:9990 &
cd EPF-webapp
mvn clean install -U
cd ../
cd EPF-security-auth-webapp
mvn clean install -U
cd ../
cd EPF-security-webapp
mvn clean install -U
cd ../
cd EPF-workflow-webapp
mvn clean install -U
cd ../
cd EPF-tests
./dev.sh
./test.sh