. ../../env.sh ../../
../../registry.sh ghcr.io "kubeelasti/elasti-operator:0.1.31-rc2"
../../registry.sh ghcr.io "kubeelasti/elasti-resolver:0.1.31-rc2"
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install elasti oci://ghcr.io/kubeelasti/charts/elasti --version "0.1.31-rc2" --namespace elasti --create-namespace --wait -f values.yaml \
	--set "global.image.registry=$EPF_CLUSTER_IMAGE_REGISTRY"