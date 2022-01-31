. ./config.sh
~/kafka_2.13-2.8.1/bin/zookeeper-server-start.sh ~/kafka_2.13-2.8.1/config/zookeeper.properties &
~/jaeger-1.24.0-linux-amd64/jaeger-all-in-one &
export JAVA_HOME=~/jdk8u312-b07
~/pluto-3.1.0/bin/startup.sh &
export JAVA_HOME=~/jdk-11.0.13+8
~/wildfly-24.0.1.Final/bin/standalone.sh "-Djboss.http.port=8585" "-Djboss.https.port=8686" --debug &
Xvfb :10 -ac &
export DISPLAY=:10
~/kafka_2.13-2.8.1/bin/kafka-server-start.sh ~/kafka_2.13-2.8.1/config/server.properties &