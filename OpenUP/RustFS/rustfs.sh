. ../env.sh ../
helm repo add rustfs https://charts.rustfs.com --force-update
../secret.sh rustfs
../registry.sh docker.io "rustfs/rustfs:1.0.4"
../registry.sh docker.io "library/busybox:stable"
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install rustfs rustfs/rustfs --version 1.0.4 -f values-rustfs.yaml --wait \
	--set "image.rustfs.repository=$EPF_CLUSTER_IMAGE_REGISTRY/rustfs/rustfs" \
	--set "image.initImage.repository=$EPF_CLUSTER_IMAGE_REGISTRY/library/busybox" \
	--set "ingress.hosts[0].host=$MANAGEMENT_STORAGE_DOMAIN" \
	--set "ingress.hosts[1].host=$STORAGE_DOMAIN"