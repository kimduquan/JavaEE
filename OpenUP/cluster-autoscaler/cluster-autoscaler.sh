helm repo add autoscaler https://kubernetes.github.io/autoscaler
helm repo update
. ../env.sh
kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} apply -f https://raw.githubusercontent.com/projectcalico/calico/v3.32.1/manifests/calico.yaml
kubectl delete secret epf-cluster
kubectl create secret generic epf-cluster --from-file=value=${EPF_CLUSTER_KUBE_CONFIG}
helm upgrade --install --wait cluster-autoscaler autoscaler/cluster-autoscaler -f values-cluster-autoscaler.yaml
VM_IP=$(kubectl get vmi --no-headers | awk '{print $4}')
ssh -i ${EPF_CLUSTER_SSH_KEY} capk@${VM_IP}
