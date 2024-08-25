setlocal
call ../env.bat
call config.bat
copy %SOURCE_DIR%\public.pem .\src\main\jib\
:: call mvn quarkus:dev -Ddebug=5007
call mvn clean install -U -Dquarkus.container-image.build=true
call stop.bat
call start.bat
endlocal