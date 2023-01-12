del .\EPF-query.log.*
setlocal
call ../env.bat
call ../config.bat
call config.bat
copy %USER_DIR%\EPF-query.trace.db .\src\main\jib\home\jboss\
copy %USER_DIR%\EPF-query.mv.db .\src\main\jib\home\jboss\
call mvn clean install -U -Dquarkus.container-image.build=true
call stop.bat
call start.bat
endlocal