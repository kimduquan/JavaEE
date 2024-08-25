setlocal
call .\env.bat
Taskkill /IM EPF-gateway-* /F
Taskkill /IM EPF-persistence-* /F
Taskkill /IM EPF-shell-* /F
cd EPF-tests
call mvn liberty:stop
cd ../
set CUR_DIR=%CD%
cd %KAFKA_HOME%
call .\kafka-server-stop.bat &
Taskkill /IM jaeger-all-in-one.exe /F
Taskkill /IM geckodriver.exe /F
Taskkill /IM firefox.exe /F
set JAVA_HOME=%JAVA8_HOME%
cd %PLUTO_HOME%\bin
call .\shutdown.bat &
cd %CUR_DIR%
set NOPAUSE=true
cd %WILDFLY_HOME%
call .\jboss-cli.bat --connect command=:shutdown &
cd %CUR_DIR%
cd %KAFKA_HOME%
call .\zookeeper-server-stop.bat &
cd %CUR_DIR%
Taskkill /IM java.exe /F
endlocal
