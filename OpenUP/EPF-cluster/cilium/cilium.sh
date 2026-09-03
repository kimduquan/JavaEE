. ../../env.sh
helm repo add cilium https://helm.cilium.io/
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install --wait cilium cilium/cilium --version 1.20.1 --namespace=kube-system -f values-cilium.yaml