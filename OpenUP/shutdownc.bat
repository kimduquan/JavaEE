setlocal
call .\env.bat
Taskkill /IM EPF-shell-* /F
Taskkill /IM geckodriver.exe /F
Taskkill /IM firefox.exe /F
Taskkill /IM java.exe /F

Taskkill /IM kubectl.exe /F
call webapp_shutdownc.bat
cd EPF-gateway
call stop.bat
cd ../
cd EPF-registry
call stop.bat
cd ../
cd EPF-net
call stop.bat
cd ../
cd EPF-tests
call stop.bat
cd ../
cd EPF-query
call stop.bat
cd ../
cd EPF-persistence
call stop.bat
cd ../
cd EPF-cache
call stop.bat
cd ../
cd EPF-logging
call stop.bat
cd ../
cd EPF-messaging
call stop.bat
cd ../
cd EPF-config
call stop.bat
cd ../
cd EPF-transaction
call stop.bat
cd ../
endlocal
