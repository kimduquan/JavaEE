del .\EPF-gateway.log.*
setlocal
call ../env.bat
call ../config.bat
call config.bat
java -version
call mvn clean install -Depf-gateway-native -U
call .\target\EPF-gateway-1.0.0-runner.exe
endlocal