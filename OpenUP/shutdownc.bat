setlocal
call .\env.bat
Taskkill /IM EPF-shell-* /F
Taskkill /IM geckodriver.exe /F
Taskkill /IM firefox.exe /F
Taskkill /IM java.exe /F

Taskkill /IM kubectl.exe /F
cd EPF-webapp
call stop.bat
cd ../
cd EPF-gateway
call stop.bat
cd ../
cd EPF-tests
call stop.bat
cd ../
cd EPF-persistence
call stop.bat
cd ../
cd EPF-transaction
call stop.bat
cd ../
cd EPF-transaction-internal
call stop.bat
cd ../
kubectl delete -f ./kafka.yaml
kubectl wait --for=delete -f ./kafka.yaml
kubectl delete -f ./zookeeper.yaml
kubectl wait --for=delete -f ./zookeeper.yaml
kubectl delete -f ./postgresql.yaml
kubectl wait --for=delete -f ./postgresql.yaml
endlocal
