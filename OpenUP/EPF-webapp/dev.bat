setlocal
call ../env.bat
call ../config.bat
call mvn clean install -U
endlocal