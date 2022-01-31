. ./env.sh
. ./config.sh
$KAFKA_HOME$/zookeeper-server-start.sh $KAFKA_DIR$/config/zookeeper.properties &
$JAEGER_HOME$/jaeger-all-in-one &
export JAVA_HOME=$JAVA8_HOME$
$PLUTO_HOME$/bin/startup.sh &
$WILDFLY_HOME$/standalone.sh "-Djboss.http.port=80" "-Djboss.https.port=443" --debug &
Xvfb :10 -ac &
export DISPLAY=:10
$KAFKA_HOME$/kafka-server-start.sh $KAFKA_DIR$/config/server.properties &
export JAVA_HOME=$JAVA11_HOME$