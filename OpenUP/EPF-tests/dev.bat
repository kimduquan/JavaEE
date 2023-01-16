mkdir -p "./target/.libertyDevc/apps/"
copy "./target/servers/test/apps" "./target/.libertyDevc/apps/"
mkdir -p "./target/servers/test/epf.file.root/"

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
call mvn package -U -P Container
call docker build -t openup:1.0.0 ./
call stop.bat
call start.bat
endlocal