kubectl apply -f https://raw.githubusercontent.com/OpenLiberty/open-liberty-operator/main/deploy/releases/0.8.2/kubectl/openliberty-app-crd.yaml
set OPERATOR_NAMESPACE=default
set WATCH_NAMESPACE=\"\"
curl https://raw.githubusercontent.com/OpenLiberty/open-liberty-operator/main/deploy/releases/0.8.2/kubectl/openliberty-app-rbac-watch-all.yaml -o openliberty-app-rbac-watch-all.yaml
powershell -Command "(gc .\openliberty-app-rbac-watch-all.yaml) -replace 'OPEN_LIBERTY_OPERATOR_NAMESPACE', '%OPERATOR_NAMESPACE%' | Out-File -encoding ASCII .\openliberty-app-rbac-watch-all.yaml"
kubectl apply -f .\openliberty-app-rbac-watch-all.yaml
curl https://raw.githubusercontent.com/OpenLiberty/open-liberty-operator/main/deploy/releases/0.8.2/kubectl/openliberty-app-operator.yaml -o openliberty-app-operator.yaml
powershell -Command "(gc .\openliberty-app-operator.yaml) -replace 'OPEN_LIBERTY_WATCH_NAMESPACE', '%WATCH_NAMESPACE%' | Out-File -encoding ASCII .\openliberty-app-operator.yaml"
kubectl apply -n %OPERATOR_NAMESPACE% -f .\openliberty-app-operator.yaml