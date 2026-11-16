kubectl --kubeconfig=epf-management.kubeconfig get secret cilium-ca -n kube-system -o jsonpath='{.data.ca\.crt}' | base64 -d > epf-management.crt
kubectl --kubeconfig=epf-cluster.kubeconfig get secret cilium-ca -n kube-system -o jsonpath='{.data.ca\.crt}' | base64 -d > epf-cluster.crt
cat epf-management.crt epf-cluster.crt > ca-bundle.crt
kubectl --kubeconfig=epf-management.kubeconfig delete secret cilium-root-ca.crt --wait
kubectl --kubeconfig=epf-management.kubeconfig create secret generic cilium-root-ca.crt --from-file=ca.crt=ca-bundle.crt -n kube-system
kubectl --kubeconfig=epf-cluster.kubeconfig delete secret cilium-root-ca.crt --wait
kubectl --kubeconfig=epf-cluster.kubeconfig create secret generic cilium-root-ca.crt --from-file=ca.crt=ca-bundle.crt -n kube-system