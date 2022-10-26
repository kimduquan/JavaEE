setlocal
call ..\env.bat
call mvn liberty:stop
kubectl delete -f ./openup.yaml
kubectl wait --for=delete -f ./openup.yaml
endlocal