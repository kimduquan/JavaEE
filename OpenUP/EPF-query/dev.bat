setlocal
call ../env.bat
call ../config.bat
call mvn clean install -U
call mvn quarkus:dev -Ddebug=5188
endlocal