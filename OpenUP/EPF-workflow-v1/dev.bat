setlocal
call ../env.bat
cd ../EPF-gateway
call mvn clean install -U
copy %SOURCE_DIR%\dev.p12 .\
copy %SOURCE_DIR%\public.pem .\
start %JAVA_HOME%/bin/java.exe -Dquarkus.http.port=8081 -jar target/quarkus-app/quarkus-run.jar
cd ../EPF-workflow
set quarkus.devservices.enabled=false
call mvn clean install -U
call mvn quarkus:dev -Ddebug=5189
endlocal