setlocal
kubectl delete -f ./postgresql.yaml
kubectl get pod -l app.kubernetes.io/name=postgresql
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=postgresql
kubectl wait --for=delete -f ./postgresql.yaml
endlocal
