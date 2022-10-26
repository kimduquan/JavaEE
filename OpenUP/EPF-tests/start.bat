kubectl apply -f ./openup.yaml
kubectl wait deployment --for condition=available openup --timeout=300s
kubectl wait pod --for condition=ready -l name=openup --timeout=300s