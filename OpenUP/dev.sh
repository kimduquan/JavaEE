./shutdownc.sh
./clean.sh
./compile.sh
cd EPF-config
./start.sh
cd ../
cd EPF-messaging
./start.sh
cd ../
cd EPF-logging
./start.sh
cd ../
cd EPF-cache
./dev.sh
cd ../
cd EPF-transaction
./start.sh
cd ../
cd EPF-persistence
./dev.sh
cd ../
cd EPF-query
./dev.sh
cd ../
cd EPF-net
./start.sh
cd ../
cd EPF-registry
./start.sh
cd ../
cd EPF-concurrent
./start.sh
cd ../
cd EPF-workflow-management
./start.sh
cd ../
cd EPF-event
./start.sh
cd ../
cd EPF-workflow
./start.sh
cd ../
cd EPF-gateway
./start.sh
cd ../
cd EPF-shell
#./dev.sh
cd ../
./webapp_startupc.sh
./webapp_deploy.sh
cd EPF-tests
./dev.sh
./test.sh