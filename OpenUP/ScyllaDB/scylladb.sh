helm repo add scylla https://scylla-operator-charts.storage.googleapis.com/stable
helm repo update

helm install scylla-operator scylla/scylla-operator --create-namespace --namespace scylla-operator
kubectl wait -n scylla-operator --for=condition=ready pod -l "app.kubernetes.io/name=scylla-operator"
kubectl -n scylla-operator get all

helm install scylla-manager scylla/scylla-manager --create-namespace --namespace scylla-manager
kubectl wait -n scylla-manager --for=condition=ready pod -l "app.kubernetes.io/name=scylla-manager"
kubectl wait -n scylla-manager --for=condition=ready pod -l "app.kubernetes.io/name=scylla-manager-controller"
kubectl -n scylla-manager get all

helm install scylla scylla/scylla --create-namespace --namespace scylla
kubectl wait -n scylla --for=condition=ready pod -l "app.kubernetes.io/name=scylla"
kubectl -n scylla get all