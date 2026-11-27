. ../env.sh ../
../registry.sh registry-1.docker.io "bitnami/redis:latest"
../secret.sh redis-auth
$helm upgrade --install redis oci://registry-1.docker.io/bitnamicharts/redis --version 28.3.16 -f values-redis.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "global.defaultStorageClass=ceph-block"
$kubectl apply -f service-export.yaml