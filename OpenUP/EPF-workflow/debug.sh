. ../env.sh
cd $CASSANDRA_HOME
bin/cassandra &
cd $KAFKA_DIR
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &
