kubectl --kubeconfig=epf-management.kubeconfig get secret cilium-ca -n kube-system -o jsonpath='{.data.ca\.crt}' | base64 -d > epf-management.crt
kubectl --kubeconfig=epf-cluster.kubeconfig get secret cilium-ca -n kube-system -o jsonpath='{.data.ca\.crt}' | base64 -d > epf-cluster.crt
cat epf-management.crt epf-cluster.crt > ca-bundle.crt