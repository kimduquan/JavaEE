. ../env.sh
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

$kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" create ns $STAGING_NAMESPACE
$kubectl --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" create ns $PRODUCTION_NAMESPACE