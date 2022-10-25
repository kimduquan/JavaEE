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
kubectl delete -f ./epf-webapp.yaml
kubectl wait --for=delete -f ./epf-webapp.yaml
cd ../
cd EPF-gateway
kubectl delete -f ./epf-gateway.yaml
kubectl wait --for=delete -f ./epf-gateway.yaml
cd ../
cd EPF-tests
kubectl delete -f ./openup.yaml
kubectl wait --for=delete -f ./openup.yaml
cd ../
cd EPF-persistence
kubectl delete -f target/kubernetes/kubernetes.yml
kubectl wait --for=delete -f target/kubernetes/kubernetes.yml
cd ../
cd EPF-transaction
kubectl delete -f target/kubernetes/kubernetes.yml
kubectl wait --for=delete -f target/kubernetes/kubernetes.yml
cd ../
cd EPF-transaction-internal
kubectl delete -f target/kubernetes/kubernetes.yml
kubectl wait --for=delete -f target/kubernetes/kubernetes.yml
cd ../
kubectl delete -f ./kafka.yaml
kubectl wait --for=delete -f ./kafka.yaml
kubectl delete -f ./zookeeper.yaml
kubectl wait --for=delete -f ./zookeeper.yaml
kubectl delete -f ./postgresql.yaml
kubectl wait --for=delete -f ./postgresql.yaml
endlocal
