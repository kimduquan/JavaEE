setlocal
call .\env.bat
call .\config.bat
set CUR_DIR=%CD%
set JAVA_HOME=%JAVA8_HOME%
cd %WILDFLY_HOME%
call .\standalone.bat "-Djboss.http.port=80" "-Djboss.https.port=443" --debug &
:: https://bugs.openjdk.org/browse/JDK-8285445
:: call .\standalone.bat "-Djboss.http.port=80" "-Djboss.https.port=443" "-Djdk.io.File.enableADS=true" --debug &
cd %CUR_DIR%
set JAVA_HOME=%JAVA11_HOME%
endlocal
