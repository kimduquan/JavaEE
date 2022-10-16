setlocal
call ../env.bat
call mvn clean install -U -Dquarkus.container-image.build=true
call kubectl delete -f target/kubernetes/kubernetes.yml
call kubectl apply -f target/kubernetes/kubernetes.yml
endlocal