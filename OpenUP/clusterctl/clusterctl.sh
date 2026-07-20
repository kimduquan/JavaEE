curl -L https://github.com/kubernetes-sigs/cluster-api/releases/download/v1.13.4/clusterctl-linux-amd64 -o clusterctl
sudo install -o root -g root -m 0755 clusterctl /usr/local/bin/clusterctl
clusterctl version
export CLUSTER_TOPOLOGY=true
clusterctl init --infrastructure kubevirt --target-namespace default --wait-providers
export NODE_VM_IMAGE_TEMPLATE="quay.io/capk/ubuntu-2404-container-disk:v1.34.1"
export CAPK_GUEST_K8S_VERSION="${NODE_VM_IMAGE_TEMPLATE/*:/}"
export CRI_PATH="unix:///var/run/containerd/containerd.sock"
#clusterctl generate cluster epf-cluster --kubernetes-version ${CAPK_GUEST_K8S_VERSION} --flavor lb --control-plane-machine-count 1 --infrastructure kubevirt --target-namespace default > epf-cluster.yaml
kubectl apply -f epf-cluster.yaml
clusterctl upgrade apply --contract v1beta2 --wait-providers