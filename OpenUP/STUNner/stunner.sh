helm repo add stunner https://l7mp.io/stunner
helm repo update
kubectl apply -f stunner-crd.yaml
helm install stunner stunner/stunner --namespace=default --skip-crds