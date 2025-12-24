helm repo add openfeature https://open-feature.github.io/open-feature-operator/
helm repo update
helm install openfeature openfeature/open-feature-operator --set defaultNamespace=default --set namespace.create=false --wait
kubectl apply -f feature.yaml