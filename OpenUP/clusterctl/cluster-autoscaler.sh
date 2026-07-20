helm repo add autoscaler https://kubernetes.github.io/autoscaler
helm repo update
helm upgrade --install --wait cluster-autoscaler autoscaler/cluster-autoscaler -f values-cluster-autoscaler.yaml
