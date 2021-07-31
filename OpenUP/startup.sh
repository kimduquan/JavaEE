export JAVA_HOME=~/jdk8u292-b10
~/pluto-3.1.0/bin/startup.sh
~/jaeger-1.24.0-linux-amd64/jaeger-all-in-one &
#sudo chmod -R 1777 /tmp
Xvfb :10 -ac &
export DISPLAY=:10