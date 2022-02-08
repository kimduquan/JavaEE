call ./env.bat
call ./shutdown.bat
call ./clean.bat
call ./startup.bat
call ./config.bat
call mvn clean install -U -DskipTests -T 1C
cd EPF-gateway
start mvn quarkus:dev &
cd ../
cd EPF-persistence
start mvn quarkus:dev -Ddebug=5006 &
cd ../
cd EPF-shell
call mvn install -Depf-shell-native
cd ../
call ./install.bat &
cd EPF-tests
call mvn liberty:dev