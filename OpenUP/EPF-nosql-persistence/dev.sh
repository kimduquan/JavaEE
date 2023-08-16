. ../env.sh
export JAVA_HOME=$JAVA17_HOME
mvn clean install -U
java -jar target/quarkus-app/quarkus-run.jar