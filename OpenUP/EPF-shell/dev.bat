call ../env.bat
call ../config.bat
call mvn clean install -U -Depf-shell-native
#call mvn clean install -U
#C:\jdk-11.0.13+8\bin\java.exe -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8001 -jar .\target\quarkus-app\quarkus-run.jar security login -u any_role1@openup -p