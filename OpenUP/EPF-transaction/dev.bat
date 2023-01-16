del .\EPF-transaction.log.*
rmdir "ObjectStore" /s /q
setlocal
call ../env.bat
call config.bat
:: java -jar ./target/quarkus-app/quarkus-run.jar
call mvn clean install -U -Dquarkus.container-image.build=true
call stop.bat
call start.bat
endlocal