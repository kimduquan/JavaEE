del .\EPF-gateway.log.*
setlocal
call ../env.bat
copy %SOURCE_DIR%\dev.p12 .\src\main\jib\
call mvn clean install -U -Dquarkus.container-image.build=true
call stop.bat
call start.bat
endlocal