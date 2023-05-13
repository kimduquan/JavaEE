setlocal
call ../env.bat
call ../native_env.bat
call mvn clean package -U -Dnative
endlocal