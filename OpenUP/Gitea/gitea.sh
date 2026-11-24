. ../env.sh ../
../secret.sh gitea-smtp $NAMESPACE
../secret.sh gitea-secret $NAMESPACE
../secret.sh gitea-admin-secret $NAMESPACE
../secret.sh gitea-oauth-secret $NAMESPACE
../secret.sh redis-auth $NAMESPACE
../registry.sh docker.gitea.com "gitea/gitea:1.27.0-rootless"
GIT_MNGT_IP=$($kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG get svc -n kube-system --no-headers cilium-ingress | awk '{print $4}')
GIT_MNGT_HOST=$(echo "$GIT_MNGT_IP" | tr '.' '-')
GIT_MNGT_HOST="git-$GIT_MNGT_HOST.nip.io"
ROOT_URL="https://$GIT_MNGT_HOST"
REDIS_PASSWORD=$($kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG get secret redis-auth -o jsonpath='{.data.password}' | base64 --decode)
REDIS_CONNECTION="redis://:${REDIS_PASSWORD}@redis-master.default.svc.clusterset.local:6379/${REDIS_DATABASE}"
CACHE_HOST="redis://:${REDIS_PASSWORD}@redis-master.default.svc.clusterset.local:6379/${REDIS_DATABASE}?pool_size=100&idle_timeout=180s"
$helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG repo add gitea-charts https://dl.gitea.com/charts/
$helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install gitea gitea-charts/gitea --version 12.7.0 --wait -f values.yaml \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.repository=gitea/gitea" \
	--set "global.storageClass=cephfs" \
	--set "gitea.config.server.ROOT_URL=$ROOT_URL" \
	--set "gitea.config.session.PROVIDER_CONFIG=$REDIS_CONNECTION" \
	--set "gitea.config.cache.HOST=$CACHE_HOST" \
	--set "gitea.config.queue.CONN_STR=$REDIS_CONNECTION" \
	--set "ingress.hosts[0].host=$GIT_MNGT_HOST"