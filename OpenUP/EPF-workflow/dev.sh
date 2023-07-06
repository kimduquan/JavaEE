. ../env.sh
mvn clean install -U
cd ../
cd EPF-messaging
mvn clean install -U
java -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-transaction
mvn clean install -U
java -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-registry
mvn clean install -U
java -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-gateway
mvn clean install -U
java -Dquarkus.http.port=8081 -jar target/quarkus-app/quarkus-run.jar &
cd ../