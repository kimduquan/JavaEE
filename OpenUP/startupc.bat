setlocal
call .\env.bat
call .\config.bat
set CUR_DIR=%CD%
start kubectl port-forward svc/epf-gateway 9543:9543
start kubectl port-forward svc/epf-webapp 80:80 443:443 9990:9990
endlocal
