. ../env.sh ../
../registry.sh registry-1.docker.io "bitnami/redis:latest"
../secret.sh redis-auth
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install redis oci://registry-1.docker.io/bitnamicharts/redis --version 28.3.10 -f values-redis.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "global.defaultStorageClass=ceph-rbd"
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f services.yaml