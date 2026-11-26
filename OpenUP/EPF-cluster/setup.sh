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

cd Supavisor
./supavisor.sh
cd tenants
./tenants.sh
cd ../../

cd Prometheus
./prometheus.sh
cd ../

cd Jaeger
./jaeger.sh
cd ../

cd frp/frpc
export DEPLOYMENT=production
./frpc.sh
export DEPLOYMENT=staging
./frpc.sh
export DEPLOYMENT=dev
./frpc.sh
cd ../../

cd Keycloak
./keycloak.sh
cd ../

cd Gitea
./gitea.sh
cd ../

cd NGINX
./nginx.sh
cd ../

cd WildFly
./wildfly.sh
cd ../

cd RustFS
./rustfs.sh
cd ../
