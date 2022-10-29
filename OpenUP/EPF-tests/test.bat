setlocal
call ../env.bat
call ../config.bat
copy %SOURCE_DIR%\dev.p12 .\
call mvn liberty:dev -U -P Container -DskipTestServer=true
endlocal