Taskkill /IM kubectl.exe /F
::kubectl delete -f ./epf-gateway.yaml
::kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-gateway
::kubectl wait --for=delete -f ./epf-gateway.yaml
kubectl delete deployment -l app.kubernetes.io/name=epf-gateway
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=epf-gateway
kubectl wait deployment --for=delete -l app.kubernetes.io/name=epf-gateway