setlocal
call ../env.bat
call mvn clean install -U -Dquarkus.container-image.build=true
call stop.bat
call start.bat
endlocal