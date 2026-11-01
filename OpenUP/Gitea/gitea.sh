. ../env.sh
../secret.sh gitea-secret
../registry.sh docker.gitea.com "gitea/gitea:latest"
../registry.sh docker.io "bitnamilegacy/valkey-cluster:8.1.3-debian-12-r3"
../registry.sh docker.io "bitnamilegacy/os-shell:12-debian-12-r51"
../registry.sh docker.io "bitnamilegacy/redis-exporter:1.76.0-debian-12-r0"
../registry.sh docker.io "bitnamilegacy/os-shell:12-debian-12-r51"
helm repo add gitea-charts https://dl.gitea.com/charts/
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install gitea gitea-charts/gitea --version 12.7.0 --wait -f values.yaml \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY"