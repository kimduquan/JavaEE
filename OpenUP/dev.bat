call ./env.bat
call ./shutdown.bat
call ./clean.bat
call ./startup.bat
call mvn clean install -U -DskipTests -T 1C
cd EPF-persistence
start dev.bat &
cd ../
cd EPF-transaction-internal
start dev.bat &
cd ../
cd EPF-transaction
start dev.bat &
cd ../
cd EPF-gateway
start dev.bat &
cd ../
call ./install.bat &
cd EPF-shell
call dev.bat
cd ../
cd EPF-tests
call mvn liberty:dev