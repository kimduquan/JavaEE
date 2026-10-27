. ../../env.sh
. ../env.sh
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" --namespace $NAMESPACE delete configmap frpc
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" --namespace $NAMESPACE create configmap frpc --from-file=frpc=frpc.toml
envsubst < frps.yaml | kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" --namespace $NAMESPACE apply -f -
mvn clean package -U
HELM_VALUES="target/helm/kubernetes/frpc/values.yaml"
if [ "$DEPLOYMENT" = "dev" ]; then
    HELM_VALUES="values-dev.yaml"
fi
helm --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" --namespace $NAMESPACE upgrade --install frpc target/helm/kubernetes/frpc --wait -f $HELM_VALUES \
	--set "app.envs.FRP_PROXIES_HTTP_NAME=$DEPLOYMENT_POSTFIX-http" \
	--set "app.envs.FRP_PROXIES_HTTPS_NAME=$DEPLOYMENT_POSTFIX-https" \
	--set "app.envs.FRP_PROXIES_HTTP_LOCAL_IP=cilium-ingress.kube-system.svc.cluster.local" \
	--set "app.envs.FRP_PROXIES_HTTPS_LOCAL_IP=cilium-ingress.kube-system.svc.cluster.local"