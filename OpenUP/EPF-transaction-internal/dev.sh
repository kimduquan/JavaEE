. ../env.sh
. ./config.sh
mvn clean install -U
java -jar ./target/quarkus-app/quarkus-run.jar