./ ../env.sh
mvn liberty:stop
kubectl delete -f ./openup.yaml
kubectl get pod -l app.kubernetes.io/name=openup
kubectl wait pod --for condition=ready=false -l app.kubernetes.io/name=openup
kubectl get pod -l app.kubernetes.io/name=openup
kubectl wait --for=delete -f ./openup.yaml