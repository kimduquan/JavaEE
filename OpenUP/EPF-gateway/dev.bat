setlocal
call ../env.bat
call ../config.bat
call config.bat
call mvn quarkus:dev -Ddebug=5006
endlocal