. ../env.sh ../
../registry.sh docker.io "bitnamilegacy/kafka:4.0.0-debian-12-r10"
../secret.sh kafka-sasl
../secret.sh kafka-jaas
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install kafka oci://registry-1.docker.io/bitnamicharts/kafka --version 32.4.3 -f values-kafka.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY"