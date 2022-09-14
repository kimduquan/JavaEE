call git pull
call .\env.bat
call .\config.bat
call .\clean.bat
mvn clean package
docker pull icr.io/appcafe/open-liberty:full-java11-openj9-ubi
docker build -t openup:1.0.0 ./
kubectl apply -f deploy.yaml
kubectl get OpenLibertyApplications