. ../env.sh ../
../secret.sh smtp
../secret.sh gitea-secret
../secret.sh gitea-admin-secret
../secret.sh gitea-oauth-secret
../secret.sh redis-auth
../registry.sh docker.gitea.com "gitea/gitea:1.27.0-rootless"
OAUTH_DISCOVERY_URL="https://$DOMAIN/auth/realms/$REALM_NAME/.well-known/openid-configuration"
ROOT_URL="https://git.$DOMAIN"
REDIS_PASSWORD=$(kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG get secret redis-auth -o jsonpath='{.data.password}' | base64 --decode)
REDIS_CONNECTION="redis://:${REDIS_PASSWORD}@redis-master.default.svc.clusterset.local:6379/${REDIS_DATABASE}"
CACHE_HOST="redis://:${REDIS_PASSWORD}@redis-master.default.svc.clusterset.local:6379/${REDIS_DATABASE}?pool_size=100&idle_timeout=180s"
helm repo add gitea-charts https://dl.gitea.com/charts/
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install gitea gitea-charts/gitea --version 12.7.0 --wait -f values.yaml \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.repository=gitea/gitea" \
	--set "global.storageClass=cephfs" \
	--set "gitea.oauth[0].autoDiscoverUrl=$OAUTH_DISCOVERY_URL" \
	--set "gitea.config.server.ROOT_URL=$ROOT_URL" \
	--set "gitea.config.session.PROVIDER_CONFIG=$REDIS_CONNECTION" \
	--set "gitea.config.cache.HOST=$CACHE_HOST" \
	--set "gitea.config.queue.CONN_STR=$REDIS_CONNECTION" \
	--set "ingress.hosts[0].host=git.$PRODUCTION_DOMAIN" \
	--set "ingress.hosts[1].host=git.$STAGING_DOMAIN" \
	--set "ingress.hosts[2].host=git.$DEV_DOMAIN"