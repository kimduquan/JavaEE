helm repo add kubernetes-dashboard https://kubernetes.github.io/dashboard/
helm upgrade --install kubernetes-dashboard kubernetes-dashboard/kubernetes-dashboard --namespace default
kubectl apply -f dashboard-adminuser.yaml
kubectl apply -f dashboard-cluster-admin.yaml
kubectl apply -f dashboard-adminuser-token.yaml
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=kong
kubectl -n default port-forward svc/kubernetes-dashboard-kong-proxy 8443:443 &
kubectl -n default create token admin-user
kubectl get secret admin-user -n default -o jsonpath="{.data.token}" | base64 -d