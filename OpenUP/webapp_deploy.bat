start kubectl port-forward svc/epf-webapp-internal 9990:9990
cd EPF-webapp
call dev.bat
cd ../

cd EPF-messaging-webapp
call dev.bat
cd ../

cd EPF-persistence-webapp
call dev.bat
cd ../

cd EPF-security-auth-webapp
call dev.bat
cd ../

cd EPF-security-webapp
call dev.bat
cd ../