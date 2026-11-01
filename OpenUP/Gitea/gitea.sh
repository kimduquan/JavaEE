. ../env.sh
../secret.sh gitea-secret
../secret.sh gitea-admin-secret
../secret.sh gitea-oauth-secret
../registry.sh docker.gitea.com "gitea/gitea:1.27.0-rootless"
helm repo add gitea-charts https://dl.gitea.com/charts/
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install gitea gitea-charts/gitea --version 12.7.0 --wait -f values.yaml \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "global.repository=gitea/gitea" \
	--set "global.storageClass=cephfs" \
	--set "ingress.hosts[0].host=$PRODUCTION_DOMAIN" \
	--set "ingress.hosts[1].host=$STAGING_DOMAIN" \
	--set "ingress.hosts[2].host=$DEV_DOMAIN" \