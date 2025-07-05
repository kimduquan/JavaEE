kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.12.16/cert-manager.yaml
kubectl wait -n cert-manager --for=condition=ready pod -l app=cert-manager