#sudo unlink /var/lib/kubelet
#sudo mount --rbind /var/snap/microk8s/common/var/lib/kubelet /var/lib/kubelet
#sudo mount --make-rshared /var/lib/kubelet
#export RELEASE=$(curl https://storage.googleapis.com/kubevirt-prow/release/kubevirt/kubevirt/stable.txt)
#kubectl apply -f https://github.com/kubevirt/kubevirt/releases/download/${RELEASE}/kubevirt-operator.yaml
#kubectl apply -f https://github.com/kubevirt/kubevirt/releases/download/${RELEASE}/kubevirt-cr.yaml
kubectl apply -f kubevirt-operator.yaml
kubectl apply -f kubevirt-cr.yaml
kubectl -n kubevirt wait kv kubevirt --for condition=Available
#kubectl patch -n kubevirt kubevirt kubevirt --type merge --patch '{"spec": {"infra": {"nodePlacement": {"nodeSelector": {"node-role.kubernetes.io/control-plane": ""}}}}}'