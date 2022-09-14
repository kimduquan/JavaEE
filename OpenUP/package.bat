call git pull
call .\env.bat
call .\config.bat
call .\clean.bat
call mvn clean package
call docker pull icr.io/appcafe/open-liberty:full-java11-openj9-ubi
call docker build -t openup:1.0.0 EPF-assembly/.
call kubectl apply -f deploy.yaml
call kubectl get OpenLibertyApplications
call kubectl describe olapps/openup
call kubectl port-forward svc/openup 9080