. ./env.sh
killall EPF-shell-
killall geckodriver
killall /usr/lib/firefox/firefox
killall Xvfb
killall java

killall kubectl
./webapp_shutdownc.sh
cd EPF-gateway
./stop.sh
cd ../
cd EPF-workflow
./stop.sh
cd ../
cd EPF-event
./stop.sh
cd ../
cd EPF-workflow-management
./stop.sh
cd ../
cd EPF-file
./stop.sh
cd ../
cd EPF-concurrent
./stop.sh
cd ../
cd EPF-registry
./stop.sh
cd ../
cd EPF-net
./stop.sh
cd ../
cd EPF-tests
./stop.sh
cd ../
cd EPF-query
./stop.sh
cd ../
cd EPF-persistence
./stop.sh
cd ../
cd EPF-cache
./stop.sh
cd ../
cd EPF-logging
./stop.sh
cd ../
cd EPF-messaging
./stop.sh
cd ../
cd EPF-config
./stop.sh
cd ../
cd EPF-transaction
./stop.sh
cd ../