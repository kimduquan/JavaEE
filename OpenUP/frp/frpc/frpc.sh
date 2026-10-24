. ../../env.sh
$kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" delete configmap frpc
$kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" create configmap frpc --from-file=frpc=frpc.toml
mvn clean package -U
$helm --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" upgrade --install frpc target/helm/kubernetes/frpc --wait --set "app.envs.FRP_SERVER_ADDR=frps" --set "app.envs.FRP_PROXIES_HTTP_LOCAL_IP=cilium-ingress.kube-system.svc.cluster.local" --set "app.envs.FRP_PROXIES_HTTPS_LOCAL_IP=cilium-ingress.kube-system.svc.cluster.local"