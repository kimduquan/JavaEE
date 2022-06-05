setlocal
call .\env.bat
call .\config.bat
set CUR_DIR=%CD%
cd %KAFKA_HOME%
start .\zookeeper-server-start.bat ..\..\config\zookeeper.properties &
cd %CUR_DIR%
cd %JAEGER_HOME%
start .\jaeger-all-in-one.exe &
cd %CUR_DIR%
set JAVA_HOME=%JAVA8_HOME%
cd %PLUTO_HOME%\bin
call .\startup.bat &
cd %CUR_DIR%
cd %WILDFLY_HOME%
start .\standalone.bat "-Djboss.http.port=80" "-Djboss.https.port=443" --debug &
cd %CUR_DIR%
cd %KAFKA_HOME%
start .\kafka-server-start.bat ..\..\config\server.properties &
cd %CUR_DIR%
set JAVA_HOME=%JAVA11_HOME%
endlocal
