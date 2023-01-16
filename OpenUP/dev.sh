./shutdownc.sh
./clean.sh
./compile.sh
cd EPF-config
./dev.sh
cd ../
cd EPF-messaging
./dev.sh
cd ../
cd EPF-logging
./dev.sh
cd ../
cd EPF-cache
./dev.sh
cd ../
cd EPF-transaction
./dev.sh
cd ../
cd EPF-persistence
./dev.sh
cd ../
cd EPF-query
./dev.sh
cd ../
cd EPF-net
./dev.sh
cd ../
cd EPF-registry
./dev.sh
cd ../
cd EPF-gateway
./dev.sh
cd ../
cd EPF-shell
./dev.sh
cd ../
./webapp_startupc.sh
./webapp_deploy.sh
cd EPF-tests
./dev.sh
./test.sh