setlocal
call ../env.bat
cd ../EPF-gateway
mvn clean install -U
copy %SOURCE_DIR%\dev.p12 .\
copy %SOURCE_DIR%\public.pem .\
java -Dquarkus.http.port=8081 -jar target/quarkus-app/quarkus-run.jar &
cd ../EPF-workflow
set quarkus.devservices.enabled=false
mvn clean install -U
mvn quarkus:dev -Ddebug=5007
endlocal