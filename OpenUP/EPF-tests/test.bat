setlocal
call ../env.bat
call ../config.bat
copy %SOURCE_DIR%\dev.p12 .\
copy %USER_DIR%\geckodriver.exe .\
set mp.messaging.connector.liberty-kafka.bootstrap.servers=localhost:9092
call mvn clean install -U
call mvn liberty:dev -U -DskipTestServer=true
endlocal