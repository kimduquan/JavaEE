. ../env.sh ../
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic kafka --from-literal=jaas='org.apache.kafka.common.security.plain.PlainLoginModule required username="user1" password="090323508";'
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install kafka oci://registry-1.docker.io/bitnamicharts/kafka --version 32.4.3 -f values-kafka.yaml --wait