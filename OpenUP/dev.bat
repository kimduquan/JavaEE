call ./shutdown.bat
call ./clean.bat
call ./startup.bat
cd EPF-gateway
call mvn clean install -Depf-gateway-native
cd ../
cd EPF-shell
call mvn clean install -Depf-shell-native
cd ../
cd EPF-tests
call mvn liberty:dev