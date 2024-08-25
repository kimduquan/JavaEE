kubectl delete -f ./jaeger.yaml
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=jaeger
kubectl wait --for=delete -f ./jaeger.yaml

kubectl apply -f ./jaeger.yaml
kubectl wait deployment --for condition=available jaeger --timeout=300s
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=jaeger --timeout=300s