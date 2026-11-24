. ../env.sh ../
$helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG repo add rustfs https://charts.rustfs.com --force-update
../secret.sh rustfs
../registry.sh docker.io "rustfs/rustfs:1.0.4"
../registry.sh docker.io "library/busybox:stable"
MNGT_IP=$($kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG get svc -n kube-system --no-headers cilium-ingress | awk '{print $4}')
MNGT_HOST=$(echo "$MNGT_IP" | tr '.' '-')
MNGT_HOST="file-$MNGT_HOST.nip.io"
$helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install rustfs rustfs/rustfs --version 1.0.4 -f values-rustfs.yaml --wait \
	--set "image.rustfs.repository=$EPF_CLUSTER_IMAGE_REGISTRY/rustfs/rustfs" \
	--set "image.initImage.repository=$EPF_CLUSTER_IMAGE_REGISTRY/library/busybox" \
	--set "ingress.hosts[0].host=$MNGT_HOST" \
	--set "ingress.hosts[1].host=$PRODUCTION_STORAGE_DOMAIN" \
	--set "ingress.hosts[2].host=$STAGING_STORAGE_DOMAIN" \
	--set "ingress.hosts[3].host=$DEV_STORAGE_DOMAIN"