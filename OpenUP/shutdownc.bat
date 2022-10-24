setlocal
call .\env.bat
Taskkill /IM EPF-shell-* /F
cd EPF-tests
call mvn liberty:stop
cd ../
Taskkill /IM geckodriver.exe /F
Taskkill /IM firefox.exe /F
Taskkill /IM java.exe /F
endlocal
