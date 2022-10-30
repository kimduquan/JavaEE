setlocal
call ../env.bat
call ../config.bat
copy %SOURCE_DIR%\dev.p12 .\
copy %SOURCE_DIR%\private.pem.pem .\
copy %SOURCE_DIR%\public.pem .\
call mvn liberty:dev -U -P Container -DskipTestServer=true
endlocal