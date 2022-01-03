./config.sh
~/kafka_2.13-2.8.1/bin/zookeeper-server-start.sh ~/kafka_2.13-2.8.1/config/zookeeper.properties &
export JAVA_HOME=~/jdk8u312-b07
~/pluto-3.1.0/bin/startup.sh &
jshell ./EPF-shell/bin/start.jsh -R-Dcmd="/payara5/glassfish/bin/startserv" &
export JAVA_HOME=~/jdk-11.0.13+8
~/jaeger-1.24.0-linux-amd64/jaeger-all-in-one &
Xvfb :10 -ac &
export DISPLAY=:10
~/kafka_2.13-2.8.1/bin/kafka-server-start.sh ~/kafka_2.13-2.8.1/config/server.properties &