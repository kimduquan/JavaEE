helm repo add stunner https://l7mp.io/stunner
helm repo update
helm install stunner stunner/stunner --namespace=default --skip-crds