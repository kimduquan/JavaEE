setlocal
call ../env.bat
call config.bat
rmdir "ObjectStore" /s /q
call mvn clean install -U
java -jar ./target/quarkus-app/quarkus-run.jar
endlocal