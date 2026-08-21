sudo apt-get update
sudo apt-get install -y --no-install-recommends ca-certificates curl gnupg2
curl -fsSL https://nvidia.github.io/libnvidia-container/gpgkey | sudo gpg --dearmor -o /usr/share/keyrings/nvidia-container-toolkit-keyring.gpg && curl -s -L https://nvidia.github.io/libnvidia-container/stable/deb/nvidia-container-toolkit.list | sed 's#deb https://#deb [signed-by=/usr/share/keyrings/nvidia-container-toolkit-keyring.gpg] https://#g' | sudo tee /etc/apt/sources.list.d/nvidia-container-toolkit.list
sudo apt-get update
sudo apt-get install -y nvidia-container-toolkit
sudo nvidia-ctk runtime configure --runtime=containerd

cd MicroK8s
./microk8s.sh

cd ../
cd cert-manager
./cert-manager.sh

cd ../
cd KubeVirt
./kubevirt.sh

cd ../
cd Containerized-Data-Importer
./containerized-data-importer.sh

cd ../
cd Rook-Ceph
./rook-ceph.sh
./rook-ceph-cluster.sh

cd ../
cd clusterctl
./clusterctl.sh

cd ../
cd EPF-cluster
./epf-cluster.sh

cd ../
cd cluster-autoscaler
./cluster-autoscaler.sh

cd ../
cd metrics-server
./metrics-server.sh

cd ../
cd HAProxy
./haproxy.sh

cd ../
cd Prometheus
#./prometheus.sh

cd ../
cd Jaeger
#./jaeger.sh

cd ../
cd OpenFeature
#./openfeature.sh

cd ../
cd NVIDIA-device-plugin
#./nvidia-device-plugin.sh

cd ../
cd KubeAI
#./kubeai.sh

cd ../
cd WildFly
#./wildfly.sh

cd ../
cd Redis
#./redis.sh

cd ../
cd RustFS
#./rustfs.sh

cd ../
cd ScyllaDB
#./scylladb.sh

cd ../
cd NATS
#./nats.sh

cd ../
cd PostgreSQL
#./postgresql.sh

cd ../
cd Supavisor
#./supavisor.sh

cd ../
cd MongoDB
#./mongodb.sh

cd ../
cd Kafka
#./kafka.sh

#cd ../
#cd Neo4j
#./neo4j.sh