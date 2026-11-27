. env.sh ./

cd MicroK8s/Rook-Ceph
./rook-ceph.sh
cd ../../

cd cert-manager
./cert-manager.sh
cd ../

cd KubeVirt
./kubevirt.sh
cd ../

cd Containerized-Data-Importer
./containerized-data-importer.sh
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

cd Descheduler
./descheduler.sh
cd ../