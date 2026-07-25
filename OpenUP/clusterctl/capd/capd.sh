export CLUSTER_TOPOLOGY=true
clusterctl init --infrastructure docker --target-namespace default --wait-providers
rm epf-cluster.yaml
clusterctl generate cluster epf-cluster --kubernetes-version v1.35.1 --flavor development --target-namespace default --control-plane-machine-count=1 --worker-machine-count=1 > epf-cluster.yaml
kubectl apply -f epf-cluster.yaml
rm epf-cluster.kubeconfig
clusterctl get kubeconfig epf-cluster > epf-cluster.kubeconfig
kubectl --kubeconfig=epf-cluster.kubeconfig apply -f https://raw.githubusercontent.com/projectcalico/calico/v3.26.1/manifests/calico.yaml