export CLUSTER_TOPOLOGY=true
clusterctl init --infrastructure kubevirt --target-namespace default --wait-providers
export NODE_VM_IMAGE_TEMPLATE="quay.io/capk/ubuntu-2404-container-disk:v1.34.1"
export CAPK_GUEST_K8S_VERSION="${NODE_VM_IMAGE_TEMPLATE/*:/}"
export CRI_PATH="unix:///var/run/containerd/containerd.sock"
#rm epf-cluster.yaml
#clusterctl generate cluster epf-cluster --kubernetes-version ${CAPK_GUEST_K8S_VERSION} --flavor lb-kccm --control-plane-machine-count 1 --infrastructure kubevirt --target-namespace default > epf-cluster.yaml
kubectl apply -f epf-cluster.yaml
kubectl wait cluster epf-cluster --for condition=InfrastructureReady --timeout=1200s
clusterctl upgrade apply --contract v1beta2 --wait-providers
kubectl wait cluster epf-cluster --for condition=RemoteConnectionProbe --timeout=1200s
. ../env.sh
rm ${EPF_CLUSTER_KUBE_CONFIG}
clusterctl get kubeconfig epf-cluster > ${EPF_CLUSTER_KUBE_CONFIG}
rm ${EPF_CLUSTER_SSH_KEY}
kubectl get secret epf-cluster-ssh-keys -o jsonpath='{.data.key}' | base64 --decode > ${EPF_CLUSTER_SSH_KEY}
chmod 600 ${EPF_CLUSTER_SSH_KEY}

VM_IP=$(kubectl get vmi --no-headers | awk '{print $4}')
ssh -i ${EPF_CLUSTER_SSH_KEY} -o StrictHostKeyChecking=accept-new capk@${VM_IP} 'bash -s' < disk.sh

kubectl wait cluster epf-cluster --for condition=RemoteConnectionProbe --timeout=1200s

cd Calico
./calico.sh
cd ../

kubectl wait cluster epf-cluster --for condition=Available --timeout=1200s

cd Rook-Ceph
./rook-ceph.sh
cd ../

./setup.sh