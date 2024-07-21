setlocal
call ../env.bat
call ../config.bat
copy %SOURCE_DIR%\dev.p12 .\
copy %SOURCE_DIR%\private.pem .\
copy %SOURCE_DIR%\public.pem .\
call mvn clean install -U -DskipITs
call mvn liberty:start
endlocal