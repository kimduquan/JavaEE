#export RELEASE=$(curl https://storage.googleapis.com/kubevirt-prow/release/kubevirt/kubevirt/stable.txt)
#kubectl apply -f https://github.com/kubevirt/kubevirt/releases/download/v1.9.0/kubevirt-operator.yaml
#kubectl apply -f https://github.com/kubevirt/kubevirt/releases/download/v1.9.0/kubevirt-cr.yaml
kubectl apply -f kubevirt-operator.yaml
kubectl apply -f kubevirt-cr.yaml
kubectl -n kubevirt wait kv kubevirt --for condition=Available --timeout=600s
kubectl patch -n kubevirt kubevirt kubevirt --type merge --patch '{"spec": {"infra": {"nodePlacement": {"nodeSelector": {"node-role.kubernetes.io/control-plane": ""}}}, "workloads": {"nodePlacement": {"nodeSelector": {"kubernetes.io/hostname": "desktop-q9gd575"}}}}}'
kubectl -n kubevirt wait kv kubevirt --for condition=Available --timeout=600s