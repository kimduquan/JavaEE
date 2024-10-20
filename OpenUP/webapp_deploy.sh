. ./env.sh
kubectl port-forward svc/wildfly-internal 9990:9990 &
cd EPF-webapp
mvn wildfly:deploy
cd ../
cd EPF-messaging-webapp
mvn wildfly:deploy
cd ../
cd EPF-persistence-webapp
mvn wildfly:deploy
cd ../
cd EPF-security-auth-webapp
mvn wildfly:deploy
cd ../
cd EPF-security-webapp
mvn wildfly:deploy
cd ../
cd EPF-workflow-webapp
mvn wildfly:deploy
cd ../