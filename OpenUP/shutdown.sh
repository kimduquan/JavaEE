~/kafka_2.13-2.8.1/bin/kafka-server-stop.sh
killall jaeger-all-in-one
killall geckodriver
killall /usr/lib/firefox/firefox
killall Xvfb
export JAVA_HOME=~/jdk8u312-b07
~/pluto-3.1.0/bin/shutdown.sh &
/payara5/glassfish/bin/stopserv &
~/kafka_2.13-2.8.1/bin/zookeeper-server-stop.sh &