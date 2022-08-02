setlocal
call ../env.bat
call config.bat
call mvn quarkus:dev -Ddebug=5008
endlocal