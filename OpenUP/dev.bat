call ./env.bat
call ./shutdown.bat
call ./clean.bat
call ./startup.bat
cd EPF-gateway
call mvn clean install -U
start mvn quarkus:dev &
cd ../
cd EPF-persistence
call mvn clean install -U
start mvn quarkus:dev -Ddebug=5006 &
cd ../
cd EPF-security
call mvn clean install -U
start mvn quarkus:dev -Ddebug=5007 &
cd ../
cd EPF-shell
call mvn clean install -U -Depf-shell-native &
cd ../
call ./install.bat &
cd EPF-tests
call mvn liberty:dev