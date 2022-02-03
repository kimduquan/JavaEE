. ./env.sh
./shutdown.sh
./clean.sh
./startup.sh
. ./config.sh
cd EPF-gateway
mvn clean install -U
mvn quarkus:dev &
cd ../
cd EPF-persistence
mvn clean install -U
mvn quarkus:dev &
cd ../
cd EPF-shell
mvn clean install -U -Depf-shell-native &
cd ../
./install.sh &
cd EPF-tests
mvn liberty:dev