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
cd EPF-webapp
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
cd EPF-tests
./dev.sh
./test.sh