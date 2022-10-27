kubectl apply -f ./postgresql.yaml
kubectl get deployment postgresql
kubectl wait deployment --for condition=available postgresql
kubectl get deployment postgresql
kubectl get pod -l app.kubernetes.io/name=postgresql
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=postgresql
kubectl get pod -l app.kubernetes.io/name=postgresql