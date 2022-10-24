docker build -t epf-webapp:1.0.0 ./
kubectl delete -f epf-webapp.yml
kubectl apply -f epf-webapp.yml