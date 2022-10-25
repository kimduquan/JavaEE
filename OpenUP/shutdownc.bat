setlocal
call .\env.bat
Taskkill /IM EPF-shell-* /F
cd EPF-tests
call mvn liberty:stop
cd ../
Taskkill /IM geckodriver.exe /F
Taskkill /IM firefox.exe /F
Taskkill /IM java.exe /F

Taskkill /IM kubectl.exe /F
cd EPF-webapp
call kubectl delete -f ./epf-webapp.yaml
call kubectl wait --for=delete -f ./epf-webapp.yaml
cd ../
cd EPF-gateway
call kubectl delete -f ./epf-gateway.yaml
call kubectl wait --for=delete -f ./epf-gateway.yaml
cd ../
cd EPF-tests
call kubectl delete -f ./openup.yaml
call kubectl wait --for=delete -f ./openup.yaml
cd ../
cd EPF-persistence
call kubectl delete -f target/kubernetes/kubernetes.yml
call kubectl wait --for=delete -f target/kubernetes/kubernetes.yml
cd ../
cd EPF-transaction
call kubectl delete -f target/kubernetes/kubernetes.yml
call kubectl wait --for=delete -f target/kubernetes/kubernetes.yml
cd ../
cd EPF-transaction-internal
call kubectl delete -f target/kubernetes/kubernetes.yml
call kubectl wait --for=delete -f target/kubernetes/kubernetes.yml
cd ../
call kubectl delete -f ./kafka.yaml
call kubectl wait --for=delete -f ./kafka.yaml
call kubectl delete -f ./zookeeper.yaml
call kubectl wait --for=delete -f ./zookeeper.yaml
call kubectl delete -f ./postgresql.yaml
call kubectl wait --for=delete -f ./postgresql.yaml
endlocal
