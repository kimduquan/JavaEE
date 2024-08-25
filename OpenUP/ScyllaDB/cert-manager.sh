kubectl apply -f https://github.com/cert-manager/cert-manager/releases/download/v1.14.4/cert-manager.yaml
kubectl wait -n cert-manager --for=condition=ready pod -l app=cert-manager