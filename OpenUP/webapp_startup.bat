call .\env.bat
call .\config.bat
setlocal
set CUR_DIR=%CD%
set JAVA_HOME=%JAVA8_HOME%
cd %WILDFLY_HOME%
start .\standalone.bat "-Djboss.http.port=80" "-Djboss.https.port=443" --debug &
cd %CUR_DIR%
set JAVA_HOME=%JAVA11_HOME%
endlocal
