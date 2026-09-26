helm repo add autoscaler https://kubernetes.github.io/autoscaler
helm repo update
. ../env.sh
IMAGE_TAG=v1.35.0
../registry.sh registry.k8s.io "autoscaling/cluster-autoscaler:$IMAGE_TAG"
kubectl delete secret epf-cluster
kubectl create secret generic epf-cluster --from-file=value=${EPF_CLUSTER_KUBE_CONFIG}
helm upgrade --install --wait --timeout 10m cluster-autoscaler autoscaler/cluster-autoscaler -f values-cluster-autoscaler.yaml \
	--set "image.tag=$IMAGE_TAG"
