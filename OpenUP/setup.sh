. env.sh ./

cd NVIDIA-Container-Toolkit
./nvidia-container-toolkit.sh
cd ../

cd NVIDIA-device-plugin
./nvidia-device-plugin.sh
cd ../

cd CanonicalKubernetes
./cilium.sh
cd ../

cd CSI-Snapshotter
./csi-snapshotter.sh
cd ../

cd Rook-Ceph
./rook-ceph.sh
./rook-ceph-cluster.sh
./ceph-csi-drivers.sh
cd ../

cd metrics-server
./metrics-server.sh
cd ../

cd Redis
./redis.sh
cd ../

cd PostgreSQL
./postgresql.sh

cd Kafka
./kafka.sh

