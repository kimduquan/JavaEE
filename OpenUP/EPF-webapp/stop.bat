Taskkill /IM kubectl.exe /F
kubectl delete -f ./epf-webapp.yaml
kubectl get pod -l app.kubernetes.io/name=epf-webapp
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-webapp
kubectl wait --for=delete -f ./epf-webapp.yaml