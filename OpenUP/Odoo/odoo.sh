helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
helm install odoo bitnami/odoo -f values.yaml