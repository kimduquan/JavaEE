setlocal
call ../env.bat
call mvn clean install -U
call mvn wildfly:deploy
endlocal