setlocal
call ../env.bat
call ../config.bat
set mp.messaging.connector.liberty-kafka.bootstrap.servers=localhost:9092
call mvn liberty:dev -U -P Container -DskipTestServer=true
endlocal