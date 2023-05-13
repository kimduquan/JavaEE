setlocal
call ../env.bat
call ../native_env.bat
call mvn clean install -U -Dnative
endlocal