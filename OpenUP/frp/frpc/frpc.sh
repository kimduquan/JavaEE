. ../../env.sh
$kubectl --namespace kube-system delete configmap frpc
$kubectl --namespace kube-system create configmap frpc --from-file=frpc=frpc.toml
mvn clean package -U
$helm upgrade --install frpc target/helm/kubernetes/frpc --namespace kube-system --wait --set "app.envs.FRP_SERVER_ADDR=$GATEWAY_IP" --set "app.envs.FRP_PROXIES_HTTP_LOCAL_IP=cilium-ingress" --set "app.envs.FRP_PROXIES_HTTPS_LOCAL_IP=cilium-ingress"