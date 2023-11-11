setlocal
call ../env.bat
set quarkus.devservices.enabled=false
call mvn clean install -U
mvn quarkus:dev -Ddebug=5007
endlocal