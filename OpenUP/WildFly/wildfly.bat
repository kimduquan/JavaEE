setlocal
call ../env.bat
copy %SOURCE_DIR%\dev.p12 .\
docker build -t wildfly:1.0.0 ./
call stop.bat
call start.bat
endlocal