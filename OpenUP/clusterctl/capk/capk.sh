export CLUSTER_TOPOLOGY=true
rm epf-cluster.yaml
clusterctl init --infrastructure kubevirt --target-namespace default --wait-providers
export NODE_VM_IMAGE_TEMPLATE="quay.io/capk/ubuntu-2404-container-disk:v1.34.1"
export CAPK_GUEST_K8S_VERSION="${NODE_VM_IMAGE_TEMPLATE/*:/}"
export CRI_PATH="unix:///var/run/containerd/containerd.sock"
clusterctl generate cluster epf-cluster --kubernetes-version ${CAPK_GUEST_K8S_VERSION} --flavor lb --control-plane-machine-count 1 --infrastructure kubevirt --target-namespace default > epf-cluster.yaml
kubectl apply -f epf-cluster.yaml
kubectl wait cluster epf-cluster --for condition=Available
clusterctl upgrade apply --contract v1beta2 --wait-providers
#. ../../env.sh
#rm ${EPF_CLUSTER_KUBE_CONFIG}
#clusterctl get kubeconfig epf-cluster > ${EPF_CLUSTER_KUBE_CONFIG}
#kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} apply -f https://raw.githubusercontent.com/projectcalico/calico/v3.32.1/manifests/calico.yaml
#kubectl delete secret epf-cluster
#kubectl create secret generic epf-cluster --from-file=epf-cluster=${EPF_CLUSTER_KUBE_CONFIG}