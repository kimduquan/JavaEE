sudo service mysql start
sudo service postgresql start
./shutdown.sh
./clean.sh
./startup.sh
./compile.sh
. ./env.sh
gu install native-image
cd EPF-transaction-internal
./dev.sh &>/dev/null &
cd ../
cd EPF-transaction
./dev.sh &>/dev/null &
cd ../
cd EPF-persistence
./dev.sh &>/dev/null &
cd ../
cd EPF-gateway
./dev.sh &>/dev/null &
cd ../
./install.sh &
cd EPF-shell
./dev.sh &>/dev/null &
cd ../
. ./config.sh
cd EPF-tests
mvn liberty:dev