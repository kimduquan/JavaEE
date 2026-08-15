$ helm repo add cilium https://helm.cilium.io/
$ helm upgrade --install --wait cilium cilium/cilium --namespace=kube-system -f values-cilium.yaml