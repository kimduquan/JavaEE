. ../../env.sh
$KUBECTL delete configmap frpc
$KUBECTL create configmap frpc --from-file=frps=frpc.toml
mvn clean package -U
$HELM upgrade --install frpc target/helm/kubernetes/frpc --wait --set "app.envs.FRP_SERVER_ADDR=$GATEWAY_IP" --set "app.envs.PROXIES_HTTP_LOCAL_IP=haproxy-kubernetes-ingress" --set "app.envs.PROXIES_HTTPS_LOCAL_IP=haproxy-kubernetes-ingress"