setlocal
cd EPF-webapp
call mvn wildfly:deploy
cd ../
cd EPF-messaging-webapp
call mvn wildfly:deploy
cd ../
cd EPF-persistence-webapp
call mvn wildfly:deploy
cd ../
cd EPF-security-auth-webapp
call mvn wildfly:deploy
cd ../
cd EPF-security-webapp
call mvn wildfly:deploy
cd ../
cd EPF-workflow-webapp
call mvn wildfly:deploy
cd ../
endlocal