helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
helm install cassandra bitnami/cassandra --set dbUser.password=123456