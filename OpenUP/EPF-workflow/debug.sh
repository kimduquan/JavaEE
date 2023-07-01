. ../env.sh
rm -rf /tmp/kafka-logs /tmp/zookeeper /tmp/kraft-combined-logs
cd $KAFKA_DIR
bin/zookeeper-server-start.sh config/zookeeper.properties &
cd $CASSANDRA_HOME
bin/cassandra &
cd $JAEGER_HOME
./jaeger-all-in-one --collector.zipkin.host-port=:9411 &
cd $KAFKA_DIR
bin/kafka-server-start.sh config/server.properties &
