setlocal
call ./env.bat
call mvn clean install -U -DskipTests -T 1C
endlocal