kubectl apply -f ./openup.yaml
kubectl get openlibertyapplication.apps.openliberty.io/openup
kubectl wait --for condition=RECONCILED=True openlibertyapplication.apps.openliberty.io/openup
kubectl get deployment openup
kubectl wait deployment --for condition=available openup --timeout=300s
kubectl get deployment openup
kubectl get pod -l app.kubernetes.io/name=openup
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=openup --timeout=300s
kubectl get pod -l app.kubernetes.io/name=openup