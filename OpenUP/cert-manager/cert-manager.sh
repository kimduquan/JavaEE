helm repo add jetstack https://charts.jetstack.io --force-update
helm install cert-manager jetstack/cert-manager -f values-cert-manager.yaml