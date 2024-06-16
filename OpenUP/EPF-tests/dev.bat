setlocal
call ../env.bat
call ../config.bat
copy %USER_DIR%\EPF-query.trace.db .\
copy %USER_DIR%\EPF-query.mv.db .\
copy %USER_DIR%\EPF-security.mv.db .\
copy %USER_DIR%\EPF-security.trace.db .\
copy %SOURCE_DIR%\dev.p12 .\
copy %SOURCE_DIR%\private.pem .\
copy %SOURCE_DIR%\public.pem .\
call mvn clean install -U -DskipITs
call mvn liberty:start
endlocal