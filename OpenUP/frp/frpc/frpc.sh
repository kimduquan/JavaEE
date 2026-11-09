. ../../env.sh ../../
. ../env.sh
HELM_VALUES="target/helm/kubernetes/frpc/values.yaml"
KUBE_CONFIG="${EPF_CLUSTER_KUBE_CONFIG}"
SERVICE="cilium-ingress.kube-system.svc.cluster.local"
GATEWAY_SERVICE="cilium-gateway-epf-gateway.default.svc.cluster.local"
if [ "$DEPLOYMENT" = "dev" ]; then
    HELM_VALUES="values-dev.yaml"
elif [ "$DEPLOYMENT" = "staging" ]; then
    HELM_VALUES="values-stage.yaml"
elif [ "$DEPLOYMENT" = "management" ]; then
    HELM_VALUES="values-mgmt.yaml"
    KUBE_CONFIG=
    SERVICE="haproxy-kubernetes-ingress.default.svc.cluster.local"
    GATEWAY_SERVICE="$SERVICE"
fi
mvn clean package -U
envsubst < frps.yaml | kubectl --kubeconfig="${KUBE_CONFIG}" --namespace $NAMESPACE delete --wait -f -
kubectl --kubeconfig="${KUBE_CONFIG}" --namespace $NAMESPACE delete configmap frpc --wait
kubectl --kubeconfig="${KUBE_CONFIG}" --namespace $NAMESPACE create configmap frpc --from-file=frpc=frpc.toml
envsubst < frps.yaml | kubectl --kubeconfig="${KUBE_CONFIG}" --namespace $NAMESPACE apply -f -
kubectl --kubeconfig="${KUBE_CONFIG}" --namespace $NAMESPACE wait --for=jsonpath='{.endpoints[*].conditions.ready}'='true' endpointslice -l kubernetes.io/service-name=frps
kubectl --kubeconfig="${KUBE_CONFIG}" --namespace $NAMESPACE delete --wait hpa frpc
helm --kubeconfig="${KUBE_CONFIG}" --namespace $NAMESPACE upgrade --install frpc target/helm/kubernetes/frpc --wait -f $HELM_VALUES \
	--set "app.envs.FRP_PROXIES_HTTP_NAME=$DEPLOYMENT_POSTFIX-http" \
	--set "app.envs.FRP_PROXIES_HTTPS_NAME=$DEPLOYMENT_POSTFIX-https" \
	--set "app.envs.FRP_PROXIES_HTTP_LOCAL_IP=$SERVICE" \
	--set "app.envs.FRP_PROXIES_HTTPS_LOCAL_IP=$SERVICE" \
	--set "app.envs.FRP_PROXIES_HTTP_GATEWAY_NAME=$DEPLOYMENT_POSTFIX-http-gateway" \
	--set "app.envs.FRP_PROXIES_HTTPS_GATEWAY_NAME=$DEPLOYMENT_POSTFIX-https-gateway" \
	--set "app.envs.FRP_PROXIES_HTTP_GATEWAY_LOCAL_IP=$GATEWAY_SERVICE" \
	--set "app.envs.FRP_PROXIES_HTTPS_GATEWAY_LOCAL_IP=$GATEWAY_SERVICE"
kubectl --kubeconfig="${KUBE_CONFIG}" --namespace $NAMESPACE autoscale deployment frpc --max 1