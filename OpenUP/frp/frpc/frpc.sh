. ../env.sh
kubectl delete configmap frpc
kubectl create configmap frpc --from-file=frps=frpc.toml
helm upgrade --install frpc target/helm/kubernetes/frpc --wait