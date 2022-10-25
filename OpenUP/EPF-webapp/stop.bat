Taskkill /IM kubectl.exe /F
kubectl delete -f ./epf-webapp.yaml
kubectl wait --for=delete -f ./epf-webapp.yaml