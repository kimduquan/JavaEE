kubectl delete -f ./openup.yaml
kubectl wait --for=delete -f ./openup.yaml