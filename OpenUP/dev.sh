sudo service mysql start
sudo service postgresql start
. ./env.sh
./shutdown.sh
./clean.sh
./startup.sh
. ./config.sh
mvn clean install -U -DskipTests -T 1C
cd EPF-persistence
./dev.sh &>/dev/null &
cd ../
cd EPF-transaction-internal
./dev.sh &>/dev/null &
cd ../
cd EPF-transaction
./dev.sh &>/dev/null &
cd ../
cd EPF-gateway
./dev.sh &>/dev/null &
cd ../
./install.sh &
cd EPF-shell
./dev.sh &>/dev/null &
cd ../
cd EPF-tests
mvn liberty:dev