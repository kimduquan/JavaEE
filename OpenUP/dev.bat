call ./env.bat
call ./shutdown.bat
call ./clean.bat
call ./startup.bat
call ./config.bat
call mvn clean install -U -DskipTests -T 1C
cd EPF-gateway
start dev.bat &
cd ../
cd EPF-persistence
start dev.bat &
cd ../
cd EPF-shell
start mvn install -Depf-shell-native &
cd ../
call ./install.bat &
cd EPF-tests
call mvn liberty:dev