setlocal
call ../env.bat
call ../native_env.bat
call mvn clean package -U
call native-image -jar target/EPF-shell-installer-1.0.0.jar
endlocal