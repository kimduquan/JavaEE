../env.sh ../
../registry.sh "" "jaegertracing/jaeger:2.22.0"
helm repo add jaegertracing https://jaegertracing.github.io/helm-charts --force-update
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install jaeger jaegertracing/jaeger --version 4.16.1 -f values-jaeger.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY"