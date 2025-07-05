setlocal
call ./env.bat
call mvn clean install -U -DskipTests -Dmaven.test.skip=true -Dnet.bytebuddy.experimental=true -T 1C
endlocal