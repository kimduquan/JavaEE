. ../../env.sh
$KUBECTL delete configmap frpc
$KUBECTL create configmap frpc --from-file=frps=frpc.toml
mvn clean package -U
$HELM upgrade --install frpc target/helm/kubernetes/frpc --wait