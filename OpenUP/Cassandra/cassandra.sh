helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
helm uninstall cassandra
helm install cassandra bitnami/cassandra