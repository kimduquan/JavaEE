del .\EPF-transaction-internal.log.*
rmdir "ObjectStore" /s /q
setlocal
call ../env.bat
call config.bat
java -jar ./target/quarkus-app/quarkus-run.jar
endlocal