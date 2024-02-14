. ../env.sh
. ../config.sh
cd ../
cd EPF-config
mvn clean install -U
cp $SOURCE_DIR/public.pem ./
java -Dquarkus.http.port=9184 -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-messaging
mvn clean install -U
$JAVA21_HOME/bin/java -Dquarkus.http.port=9183 -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-transaction
mvn clean install -U
$JAVA21_HOME/bin/java -Dquarkus.http.port=50000 -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-registry
mvn clean install -U
java -Dquarkus.http.port=9182 -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-workflow-management
mvn clean install -U
$JAVA21_HOME/bin/java -Dquarkus.http.port=9191 -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-event
mvn clean install -U
$JAVA21_HOME/bin/java -Dquarkus.http.port=9192 -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-workflow
mvn clean install -U
$JAVA21_HOME/bin/java -Dquarkus.http.port=9189 -jar target/quarkus-app/quarkus-run.jar &
#mvn quarkus:dev -Ddebug=5007 &
cd ../
cd EPF-gateway
mvn clean install -U
cp ../dev.p12 ./
cp ../public.pem ./
$JAVA21_HOME/bin/java -Dquarkus.http.port=8081 -jar target/quarkus-app/quarkus-run.jar &
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
cp ../dev.p12 ./
cp ../private.pem ./
cp ../public.pem ./
mvn liberty:dev