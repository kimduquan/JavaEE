helm repo add autoscaler https://kubernetes.github.io/autoscaler
helm repo update
. ../env.sh
rm ${EPF_CLUSTER_KUBE_CONFIG}
clusterctl get kubeconfig epf-cluster > ${EPF_CLUSTER_KUBE_CONFIG}
kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} apply -f https://raw.githubusercontent.com/projectcalico/calico/v3.32.1/manifests/calico.yaml
kubectl delete secret epf-cluster
kubectl create secret generic epf-cluster --from-file=value=${EPF_CLUSTER_KUBE_CONFIG}
helm upgrade --install --wait cluster-autoscaler autoscaler/cluster-autoscaler -f values-cluster-autoscaler.yaml
rm epf-cluster-ssh-key
kubectl get secret epf-cluster-ssh-keys -o jsonpath='{.data.key}' | base64 --decode > epf-cluster-ssh-key
chmod 600 epf-cluster-ssh-key
VM_IP=$(kubectl get vmi --no-headers | awk '{print $4}')
ssh -i epf-cluster-ssh-key capk@${VM_IP}
