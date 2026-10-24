. ../../env.sh
$KUBECTL --kubeconfig=$KUBECONFIG delete configmap frpc
$KUBECTL --kubeconfig=$KUBECONFIG create configmap frpc --from-file=frpc=frpc.toml
mvn clean package -U
$HELM upgrade --kubeconfig=$KUBECONFIG --install frpc target/helm/kubernetes/frpc --wait --set "app.envs.FRP_SERVER_ADDR=$GATEWAY_IP" --set "app.envs.FRP_PROXIES_HTTP_LOCAL_IP=cilium-ingress.kube-system.svc.cluster.local" --set "app.envs.FRP_PROXIES_HTTPS_LOCAL_IP=cilium-ingress.kube-system.svc.cluster.local"