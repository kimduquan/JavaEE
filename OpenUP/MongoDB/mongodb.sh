helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
helm install postgresql bitnami/postgresql --set auth.postgresPassword=090323508 --set auth.database=epf
helm install mongodb bitnami/mongodb