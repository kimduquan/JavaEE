. ../env.sh ../
export KUBECONFIG=$EPF_CLUSTER_KUBE_CONFIG

cd ../

cd metrics-server
./metrics-server.sh
cd ../

export KUBECONFIG=
cd EPF-cluster

cd KEDA
./keda.sh
cd ../

cd KubeElasti
./elasti.sh
cd ../

cd ../
cd PostgreSQL
$kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} apply -f service-import.yaml
cd ../

cd Redis
$kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} apply -f service-import.yaml
cd ../

cd Kafka
$kubectl --kubeconfig=${EPF_CLUSTER_KUBE_CONFIG} apply -f service-import.yaml
cd ../

cd Prometheus
./prometheus.sh
cd ../

cd Jaeger
./jaeger.sh
cd ../

cd WildFly
./wildfly.sh
cd ../

cd RustFS
./rustfs.sh
cd ../
