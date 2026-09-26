kubectl apply -f ubuntu-2404-container-disk.yaml
kubectl wait dv ubuntu-2404-container-disk --for condition=Bound --timeout=4800s
export CLUSTER_TOPOLOGY=true
clusterctl init --bootstrap k0sproject-k0smotron --control-plane k0sproject-k0smotron --infrastructure kubevirt --target-namespace default --wait-providers
export NODE_VM_IMAGE_TEMPLATE="quay.io/capk/ubuntu-2404-container-disk:v1.34.1"
export CAPK_GUEST_K8S_VERSION="${NODE_VM_IMAGE_TEMPLATE/*:/}"
export CRI_PATH="unix:///var/run/containerd/containerd.sock"
export STORAGE_CLASS_NAME="ceph-block"
export ROOT_VOLUME_SIZE="30Gi"
clusterctl upgrade apply --contract v1beta2 --wait-providers
. ../env.sh
#rm epf-cluster.yaml
#clusterctl generate cluster epf-cluster --kubernetes-version ${CAPK_GUEST_K8S_VERSION} --flavor lb-kccm --control-plane-machine-count 1 --infrastructure kubevirt --target-namespace default > epf-cluster.yaml
#curl -L https://github.com/k0sproject/k0smotron/releases/latest/download/convert-v1beta1-to-v1beta2-linux-amd64 -o convert
#chmod +x convert
#rm epf-cluster-v1beta2.yaml
#./convert epf-cluster.yaml > epf-cluster-v1beta2.yaml
kubectl apply -f epf-cluster-v1beta2.yaml
kubectl wait cluster epf-cluster --for condition=Available --timeout=1200s

rm ${EPF_CLUSTER_KUBE_CONFIG}
clusterctl get kubeconfig epf-cluster > ${EPF_CLUSTER_KUBE_CONFIG}
rm ${EPF_CLUSTER_SSH_KEY}
kubectl get secret epf-cluster-ssh-keys -o jsonpath='{.data.key}' | base64 --decode > ${EPF_CLUSTER_SSH_KEY}
chmod 600 ${EPF_CLUSTER_SSH_KEY}