setlocal
call ./env.bat
call mvn clean install -U -DskipTests -Dmaven.test.skip=true -T 1C
endlocal