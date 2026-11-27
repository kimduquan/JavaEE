. ../env.sh ../
git clean -f -d .
kubectl --kubeconfig="$EPF_MANAGEMENT_KUBE_CONFIG" get secret cilium-ca -n kube-system -o jsonpath='{.data.ca\.crt}' | base64 -d > epf-management.crt
kubectl --kubeconfig="$EPF_CLUSTER_KUBE_CONFIG" get secret cilium-ca -n kube-system -o jsonpath='{.data.ca\.crt}' | base64 -d > epf-cluster.crt
cat epf-management.crt epf-cluster.crt > ca-bundle.crt
kubectl --kubeconfig="$EPF_MANAGEMENT_KUBE_CONFIG" delete secret cilium-root-ca.crt --wait -n kube-system
kubectl --kubeconfig="$EPF_MANAGEMENT_KUBE_CONFIG" create secret generic cilium-root-ca.crt --from-file=ca.crt=ca-bundle.crt -n kube-system
kubectl --kubeconfig="$EPF_CLUSTER_KUBE_CONFIG" delete secret cilium-root-ca.crt --wait -n kube-system
kubectl --kubeconfig="$EPF_CLUSTER_KUBE_CONFIG" create secret generic cilium-root-ca.crt --from-file=ca.crt=ca-bundle.crt -n kube-system
#kubectl --kubeconfig="$EPF_MANAGEMENT_KUBE_CONFIG" rollout restart deploy/clustermesh-apiserver ds/cilium -n kube-system
#kubectl --kubeconfig="$EPF_CLUSTER_KUBE_CONFIG" rollout restart deploy/clustermesh-apiserver ds/cilium -n kube-system
#cilium clustermesh --kubeconfig="$EPF_MANAGEMENT_KUBE_CONFIG" status
#cilium clustermesh --kubeconfig="$EPF_CLUSTER_KUBE_CONFIG" status
#sudo microk8s kubectl patch subnet ovn-default --type='merge' -p '
#{
#  "spec": {
#    "natOutgoingPolicyRules": [
#      {
#        "match": {
#          "srcIPs": "10.1.0.0/16",
#          "dstIPs": "192.168.1.0/24"
#        },
#        "action": "forward"
#      }
#    ]
#  }
#}'