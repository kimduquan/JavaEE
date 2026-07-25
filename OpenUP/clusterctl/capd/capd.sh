export CLUSTER_TOPOLOGY=true
clusterctl init --infrastructure docker --target-namespace default --wait-providers
rm epf-cluster.yaml
clusterctl generate cluster epf-cluster --kubernetes-version v1.34.1 --flavor development --target-namespace default > epf-cluster.yaml
kubectl apply -f epf-cluster.yaml