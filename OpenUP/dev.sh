./shutdown.sh
./clean.sh
./startup.sh
cd EPF-gateway
call mvn clean install -U -Depf-gateway-native
cd EPF-shell
mvn clean install -U -Depf-shell-native
cd ../
./install.sh
cd EPF-tests
mvn liberty:dev