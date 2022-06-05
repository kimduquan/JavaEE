setlocal
call ../env.bat
call ../config.bat
call config.bat
call mvn clean install -U
call mvn quarkus:dev
endlocal