setlocal
call ..\env.bat
call mvn liberty:stop
kubectl delete -f ./openup.yaml
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=openup
kubectl wait --for=delete -f ./openup.yaml
endlocal