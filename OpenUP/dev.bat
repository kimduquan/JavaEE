call ./shutdown.bat
call ./clean.bat
call ./startup.bat
setlocal
call ./env.bat
call mvn clean install -U -DskipTests -T 1C
endlocal
cd EPF-transaction-internal
start dev.bat &
cd ../
cd EPF-transaction
start dev.bat &
cd ../
cd EPF-persistence
start dev.bat &
cd ../
cd EPF-gateway
start dev.bat &
cd ../
call ./install.bat &
cd EPF-shell
call ./dev.bat
cd ../
cd EPF-tests
setlocal
call ./env.bat
call mvn liberty:dev
endlocal