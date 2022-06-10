. ../env.sh
. ./config.sh
rm -R -d ObjectStore
mvn clean install -U
java -jar ./target/quarkus-app/quarkus-run.jar