del .\EPF-gateway.log.*
setlocal
call ../env.bat
call ../native_env.bat
call ../config.bat
call config.bat
call mvn clean install -Depf-gateway-native -U
.\target\EPF-gateway-1.0.0-runner.exe
endlocal