Taskkill /IM kubectl.exe /F
kubectl delete -f ./epf-gateway.yaml
kubectl wait --for=delete -f ./epf-gateway.yaml