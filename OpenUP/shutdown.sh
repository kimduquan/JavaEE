~/kafka_2.13-2.8.1/bin/kafka-server-stop.sh
killall jaeger-all-in-one
killall Xvfb
export JAVA_HOME=~/jdk8u292-b10
~/pluto-3.1.0/bin/shutdown.sh
~/kafka_2.13-2.8.1/bin/zookeeper-server-stop.sh