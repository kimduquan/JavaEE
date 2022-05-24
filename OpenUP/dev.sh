. ./env.sh
./shutdown.sh
./clean.sh
./startup.sh
. ./config.sh
mvn clean install -U -DskipTests -T 1C
cd EPF-gateway
./dev.sh &>/dev/null &
cd ../
cd EPF-persistence
./dev.sh &>/dev/null &
cd ../
cd EPF-shell
./dev.sh &>/dev/null &
cd ../
./install.sh &
cd EPF-tests
mvn liberty:dev