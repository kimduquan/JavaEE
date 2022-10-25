kubectl delete -f target/kubernetes/kubernetes.yml
kubectl wait --for=delete -f target/kubernetes/kubernetes.yml