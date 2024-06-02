helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
helm install odoo bitnami/odoo --set postgresql.enabled=false --set postgresql.auth.username=odoo --set postgresql.auth.password=123456 --set postgresql.auth.database=odoo --set externalDatabase.host=postgresql.default.svc.cluster.local --set externalDatabase.user=odoo --set externalDatabase.password=123456 --set externalDatabase.database=odoo --set externalDatabase.postgresqlPostgresPassword=090323508