call ../config.bat
call mvn clean install -Depf-gateway-native
#call mvn clean install
#C:\jdk-11.0.13+8\bin\java.exe -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005 -jar .\target\quarkus-app\quarkus-run.jar