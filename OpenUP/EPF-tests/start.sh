kubectl apply -f ./openup.yaml
kubectl wait --for condition=RECONCILED=True openlibertyapplication.apps.openliberty.io/openup
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=openup --timeout=300s