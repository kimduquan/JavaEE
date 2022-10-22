setlocal
call ../env.bat
call ../config.bat
call mvn liberty:dev -U -P Container -DskipTestServer=true
endlocal