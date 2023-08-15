. ../env.sh
. ../config.sh
mvn clean install -U
java -Djboss.http.port=9189 -Djboss.management.http.port=9190 -jar target/EPF-workflow-1.0.0-bootable.jar &
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
java -Dquarkus.http.port=8081 -jar target/quarkus-app/quarkus-run.jar &
cd ../
cd EPF-workflow
$WILDFLY_HOME/jboss-cli.sh --file=lra.cli &
cd ../
cd EPF-tests
cp ../dev.p12 ./
cp ../private.pem ./
cp ../public.pem ./
mvn liberty:dev