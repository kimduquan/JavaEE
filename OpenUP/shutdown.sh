~/kafka_2.13-2.8.1/bin/kafka-server-stop.sh
killall jaeger-all-in-one
killall geckodriver
killall /usr/lib/firefox/firefox
killall Xvfb
export JAVA_HOME=~/jdk8u312-b07
~/pluto-3.1.0/bin/shutdown.sh &
~/wildfly-24.0.1.Final/bin/jboss-cli.sh --connect command=:shutdown &
~/kafka_2.13-2.8.1/bin/zookeeper-server-stop.sh &