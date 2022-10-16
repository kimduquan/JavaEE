del .\EPF-transaction-internal.log.*
rmdir "ObjectStore" /s /q
setlocal
call ../env.bat
call config.bat
:: java -jar ./target/quarkus-app/quarkus-run.jar
call mvn clean install -U -Dquarkus.container-image.build=true
call kubectl delete -f target/kubernetes/kubernetes.yml
call kubectl apply -f target/kubernetes/kubernetes.yml
endlocal