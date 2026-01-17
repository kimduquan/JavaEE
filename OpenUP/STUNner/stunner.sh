helm repo add stunner https://l7mp.io/stunner
helm repo update
helm install stunner stunner/stunner --namespace=default
kubectl apply --wait=true -f stunner_auth_secret.yaml
kubectl apply --wait=true -f gatewayconfig.yaml
kubectl apply --wait=true -f gatewayclass.yaml
kubectl apply --wait=true -f gateway.yaml