Authorization=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJPbmxpbmUgSldUIEJ1aWxkZXIiLCJpYXQiOjE3NzQwMTMzMjgsImV4cCI6MTgwNTU0OTMyOCwiYXVkIjoid3d3LmV4YW1wbGUuY29tIiwic3ViIjoianJvY2tldEBleGFtcGxlLmNvbSJ9.Ec31X-x1OOUTkfqduvYFG5UE6k_-9OXMYOS_U2zpi_w
POD=$(kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG get pod -l "app.kubernetes.io/name=supavisor" -o jsonpath='{.items[0].metadata.name}')
IMPORT_FILE="$1"
TENANT="$2"
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG cp "$IMPORT_FILE" "$POD:/tmp/"
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG exec -it $POD -- curl -X PUT "http://localhost:4000/api/tenants/$TENANT" -H "Content-Type: application/json" -H "Authorization: Bearer $Authorization" -v --data @"/tmp/$IMPORT_FILE"