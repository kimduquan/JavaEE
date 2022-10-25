kubectl apply -f ./openup.yaml
kubectl wait pod --for condition=ready -l name=openup --timeout=300s