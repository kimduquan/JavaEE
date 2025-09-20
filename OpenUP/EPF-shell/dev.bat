del .\EPF-shell.log.*
copy win.env .env
setlocal
call ../env.bat
call ../native_env.bat
call ../config.bat
call ../config_ssl.bat
call mvn clean install -U -Dnative
endlocal