del .\EPF-shell.log.*
copy win.env .env
setlocal
call ../env.bat
call ../native_env.bat
call ../config.bat
call ../config_ssl.bat
set QUARKUS_NATIVE_ADDITIONAL_BUILD_ARGS=--initialize-at-run-time=org.h2.fulltext.FullTextLucene\,org.h2.fulltext.FullTextLucene\$FullTextTrigger
call mvn clean install -U -Dnative
:: call mvn clean install -U
:: java -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8001 -jar .\target\quarkus-app\quarkus-run.jar messaging connect -tid VQFEAvNQw6w1qXO_8qxTUg -p persistence
endlocal