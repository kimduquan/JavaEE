export CLUSTER_TOPOLOGY=true
clusterctl init --infrastructure kubevirt --target-namespace default --wait-providers
export NODE_VM_IMAGE_TEMPLATE="quay.io/capk/ubuntu-2404-container-disk:v1.34.1"
export CAPK_GUEST_K8S_VERSION="${NODE_VM_IMAGE_TEMPLATE/*:/}"
export CRI_PATH="unix:///var/run/containerd/containerd.sock"
#clusterctl generate cluster epf-cluster --kubernetes-version ${CAPK_GUEST_K8S_VERSION} --flavor lb --control-plane-machine-count 1 --infrastructure kubevirt --target-namespace default > epf-cluster.yaml
kubectl apply -f epf-cluster.yaml
kubectl wait cluster epf-cluster --for condition=InfrastructureReady
#clusterctl upgrade apply --contract v1beta2 --wait-providers
rm epf-cluster.kubeconfig
clusterctl get kubeconfig epf-cluster > epf-cluster.kubeconfig
kubectl --kubeconfig=epf-cluster.kubeconfig apply -f https://raw.githubusercontent.com/projectcalico/calico/v3.26.1/manifests/calico.yaml