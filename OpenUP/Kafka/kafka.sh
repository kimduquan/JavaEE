. ../env.sh ../
#../registry.sh docker.io "bitnamilegacy/kafka:4.0.0-debian-12-r10"
../secret.sh kafka-sasl
../secret.sh kafka-jaas
$helm upgrade --install kafka oci://registry-1.docker.io/bitnamicharts/kafka --version 32.4.3 -f values-kafka.yaml --wait --timeout 10m
$kubectl apply -f service-export.yaml