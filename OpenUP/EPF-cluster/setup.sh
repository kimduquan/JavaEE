. ../env.sh
export KUBECONFIG=$EPF_CLUSTER_KUBE_CONFIG

cd ../

cd metrics-server
./metrics-server.sh
cd ../

cd EPF-cluster

cd KEDA
./keda.sh
cd ../

cd HAProxy
./haproxy.sh
cd ../