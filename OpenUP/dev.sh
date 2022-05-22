. ./env.sh
./shutdown.sh
./clean.sh
./startup.sh
. ./config.sh
mvn clean install -U -DskipTests -T 1C
cd EPF-gateway
mvn quarkus:dev &
cd ../
cd EPF-persistence
mvn quarkus:dev -Ddebug=5006 &
cd ../
cd EPF-shell
mvn install -Depf-shell-native &
cd ../
./install.sh &
cd EPF-tests
mvn liberty:dev