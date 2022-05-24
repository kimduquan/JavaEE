call ../env.bat
call ../config.bat
call mvn clean install -U -Depf-shell-native
:: call .\target\EPF-shell-1.0.0-runner.exe security login -u any_role1@openup.org -p
:: call mvn clean install -U
:: java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8001 -jar .\target\quarkus-app\quarkus-run.jar security login -u any_role1@openup.org -p