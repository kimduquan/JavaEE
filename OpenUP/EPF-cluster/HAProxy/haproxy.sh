. ../../env.sh
kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} create secret tls epf-cluster-tls --cert=${EPF_CLUSTER_CERT} --key=${EPF_CLUSTER_CERT_KEY}
helm --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} upgrade --install haproxy oci://ghcr.io/haproxytech/helm-charts/kubernetes-ingress -f values-kubernetes-ingress.yaml --wait --timeout 20m