. ../../env.sh ../../
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" apply --server-side -f https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/v1.6.3/config/crd/standard/gateway.networking.k8s.io_gatewayclasses.yaml
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" apply --server-side -f https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/v1.6.3/config/crd/standard/gateway.networking.k8s.io_gateways.yaml
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" apply --server-side -f https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/v1.6.3/config/crd/standard/gateway.networking.k8s.io_httproutes.yaml
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" apply --server-side -f https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/v1.6.3/config/crd/standard/gateway.networking.k8s.io_referencegrants.yaml
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" apply --server-side -f https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/v1.6.3/config/crd/standard/gateway.networking.k8s.io_grpcroutes.yaml
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" apply --server-side -f https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/v1.6.3/config/crd/standard/gateway.networking.k8s.io_backendtlspolicies.yaml
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" apply --server-side -f https://raw.githubusercontent.com/kubernetes-sigs/gateway-api/v1.6.3/config/crd/standard/gateway.networking.k8s.io_tlsroutes.yaml
kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" apply -f gateway.yaml