. ./env.sh
cd EPF-tests
mvn liberty:stop
cd ../
pkill -f target/quarkus-app/quarkus-run.jar
$KAFKA_HOME/kafka-server-stop.sh
killall jaeger-all-in-one
killall geckodriver
killall /usr/lib/firefox/firefox
killall Xvfb
$HAZELCAST_HOME/bin/hz-stop
export JAVA_HOME=$JAVA8_HOME
$PLUTO_HOME/bin/shutdown.sh &
export JAVA_HOME=$JAVA11_HOME
$WILDFLY_HOME/jboss-cli.sh --connect command=:shutdown &
$KAFKA_HOME/zookeeper-server-stop.sh &
pgrep -f cassandra | xargs kill -9
sudo mongod --shutdown &