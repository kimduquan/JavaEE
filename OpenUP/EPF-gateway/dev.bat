del .\EPF-gateway.log.*
setlocal
call ../env.bat
:: call ../config.bat
:: call mvn quarkus:dev -Ddebug=5006
call mvn clean install -U -Dquarkus.container-image.build=true
call kubectl delete -f .\epf-gateway.yaml
call kubectl apply -f .\epf-gateway.yaml
endlocal