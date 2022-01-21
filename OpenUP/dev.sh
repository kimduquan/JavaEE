./shutdown.sh
./clean.sh
./startup.sh
cd EPF-shell
mvn clean install -Depf-shell-native
cd ../
cd EPF-tests
mvn liberty:dev