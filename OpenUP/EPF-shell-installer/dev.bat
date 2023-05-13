setlocal
call ../env.bat
call ../native_env.bat
call mkdir target\staging
call mvn clean package -U -Dnative
endlocal