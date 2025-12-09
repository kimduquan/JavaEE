kubectl create secret generic smtp --from-literal=smtp-password=""
helm install odoo oci://registry-1.docker.io/bitnamicharts/odoo -f values-odoo.yaml