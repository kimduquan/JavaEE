setlocal
call ../env.bat
call mvn clean install -U
call mvn quarkus:dev -Ddebug=5182
endlocal