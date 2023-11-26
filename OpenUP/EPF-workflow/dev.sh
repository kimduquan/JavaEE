. ../env.sh
. ../config.sh
mvn clean install -U
$JAVA21_HOME/bin/java -jar target/quarkus-app/quarkus-run.jar &
#mvn quarkus:dev -Ddebug=5007 &
cd ../
cd EPF-config
mvn clean install -U
cp $SOURCE_DIR/public.pem ./
java -jar target/quarkus-app/quarkus-run.jar &
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
cp ../dev.p12 ./
cp ../public.pem ./
$JAVA21_HOME/bin/java -Dquarkus.http.port=8081 -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-tests
cp ../dev.p12 ./
cp ../private.pem ./
cp ../public.pem ./
mvn liberty:dev