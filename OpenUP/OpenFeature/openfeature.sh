helm repo add openfeature https://open-feature.github.io/open-feature-operator/
helm repo update
helm install openfeature openfeature/open-feature-operator --wait -f values-open-feature-operator.yaml
kubectl apply -f feature.yaml