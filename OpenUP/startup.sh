. ./env.sh
. ./config.sh
export CUR_DIR=$PWD
cd $KAFKA_DIR
rm -rf /tmp/kafka-logs /tmp/zookeeper /tmp/kraft-combined-logs
bin/zookeeper-server-start.sh config/zookeeper.properties &
cd $CASSANDRA_HOME
bin/cassandra &
cd $JAEGER_HOME
./jaeger-all-in-one --collector.zipkin.host-port=:9411 &
Xvfb :10 -ac &
export DISPLAY=:10
cd $KAFKA_DIR
bin/kafka-server-start.sh config/server.properties &
export JAVA_HOME=$JAVA8_HOME
$PLUTO_HOME/bin/startup.sh &
cd $WILDFLY_HOME
sudo -E ./standalone.sh "-Djboss.http.port=80" "-Djboss.https.port=443" --debug &
export JAVA_HOME=$JAVA11_HOME
cd $CUR_DIR