. ./env.sh
./shutdown.sh
./clean.sh
./startup.sh
. ./config.sh
mvn clean install -U -DskipTests -T 1C
cd EPF-gateway
./dev.sh &
cd ../
cd EPF-persistence
./dev.sh &
cd ../
cd EPF-shell
mvn install -Depf-shell-native &
cd ../
./install.sh &
cd EPF-tests
mvn liberty:dev