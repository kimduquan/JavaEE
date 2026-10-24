. ../env.sh
kubectl delete configmap frpc
kubectl create configmap frpc --from-file=frps=frpc.toml
mvn clean package -U
helm upgrade --install frpc target/helm/kubernetes/frpc --wait