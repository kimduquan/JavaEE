call ../config.bat
call mvn clean install -Depf-gateway-native
C:\jdk-11.0.13+8\bin\java.exe -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8000 -jar .\target\quarkus-app\quarkus-run.jar