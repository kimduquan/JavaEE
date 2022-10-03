del .\EPF-gateway.log.*
setlocal
call ../env.bat
:: call ../config.bat
call mvn clean install -U -Dquarkus.container-image.build=true
:: call mvn quarkus:dev -Ddebug=5006
call kubectl delete -f target/kubernetes/kubernetes.yml
call kubectl apply -f target/kubernetes/kubernetes.yml
kubectl port-forward svc/epf-gateway 9543:8443
endlocal