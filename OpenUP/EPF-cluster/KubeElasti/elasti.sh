. ../../env.sh ../../
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install elasti oci://ghcr.io/kubeelasti/charts/elasti --namespace elasti --create-namespace --wait -f values.yaml