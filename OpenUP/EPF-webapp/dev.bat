setlocal
call ../env.bat
call ../config.bat
call mvn clean install -U
docker build -t wildfly:1.0.0 ./
kubectl delete -f wildfly.yml
kubectl apply -f wildfly.yml
endlocal