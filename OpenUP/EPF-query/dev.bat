setlocal
call ../env.bat
call ../config.bat
call config.bat
mkdir -p ./src/main/jib/tmp/
copy %USER_DIR%\EPF-query.trace.db .\src\main\jib\tmp\
copy %USER_DIR%\EPF-query.mv.db .\src\main\jib\tmp\
call mvn clean install -U -Dquarkus.container-image.build=true
call stop.bat
call start.bat
endlocal