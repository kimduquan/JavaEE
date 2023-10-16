setlocal
kubectl apply --server-side -f https://raw.githubusercontent.com/OpenLiberty/open-liberty-operator/main/deploy/releases/1.2.2/kubectl/openliberty-app-crd.yaml
kubectl apply -f ./openliberty-app-operator.yaml
endlocal