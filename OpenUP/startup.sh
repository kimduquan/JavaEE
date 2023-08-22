. ./env.sh
. ./config.sh
. ./webapp_config.sh
sudo mongod &
export CUR_DIR=$PWD
cd $KAFKA_DIR
rm -rf /tmp/kafka-logs /tmp/zookeeper /tmp/kraft-combined-logs
bin/zookeeper-server-start.sh config/zookeeper.properties &
export JAVA_HOME=$JAVA8_HOME
$PLUTO_HOME/bin/startup.sh &
cd $WILDFLY_HOME
export JAVA_HOME=$JAVA17_HOME
sudo -E ./standalone.sh "-Djboss.http.port=80" "-Djboss.https.port=443" --debug &
cd $CASSANDRA_HOME
export MAX_HEAP_SIZE=1G
export HEAP_NEWSIZE=200M
bin/cassandra &
cd $JAEGER_HOME
./jaeger-all-in-one --collector.zipkin.host-port=:9411 &
Xvfb :10 -ac &
export DISPLAY=:10
cd $KAFKA_DIR
bin/kafka-server-start.sh config/server.properties &
cd $HAZELCAST_HOME
bin/hz start
cd $CUR_DIR