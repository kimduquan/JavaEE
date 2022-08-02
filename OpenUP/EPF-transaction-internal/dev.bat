setlocal
call ../env.bat
call config.bat
rmdir "ObjectStore" /s /q
java -jar ./target/quarkus-app/quarkus-run.jar
endlocal