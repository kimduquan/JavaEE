. ../env.sh
kubectl delete configmap frp
kubectl create configmap frp --from-file=frps=frps.toml
kubectl delete -f tls.yaml
kubectl apply -f tls.yaml
kubectl delete -f ingress.yml
docker build --file $FRP_SOURCE_DIR/dockerfiles/Dockerfile-for-frps --tag epf/frp:1.0.0 $FRP_SOURCE_DIR
mvn clean package -U
helm install frp target/helm/kubernetes/frp --wait
kubectl apply -f ingress.yml