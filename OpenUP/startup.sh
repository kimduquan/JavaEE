export JAVA_HOME=~/graalvm-ee-java8-21.1.0
~/pluto-3.1.0/bin/startup.sh
#~/jaeger-1.24.0-linux-amd64/jaeger-all-in-one
sudo chmod -R 1777 /tmp
Xvfb :10 -ac &
export DISPLAY=:10