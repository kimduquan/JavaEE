helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
::helm install kafka bitnami/kafka --set persistence.size=1Gi --set logPersistence.size=1Gi --set zookeeper.persistence.size=1Gi
helm install kafka bitnami/kafka -f values.yaml