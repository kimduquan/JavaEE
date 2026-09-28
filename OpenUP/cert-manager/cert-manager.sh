helm repo add jetstack https://charts.jetstack.io --force-update
helm upgrade --install cert-manager jetstack/cert-manager --version v1.21.2 -f values-cert-manager.yaml --wait