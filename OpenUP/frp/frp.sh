. ../env.sh
docker build --file $FRP_SOURCE_DIR/dockerfiles/Dockerfile-for-frps --tag epf/frp:1.0.0 $FRP_SOURCE_DIR
mvn clean package -U
helm install frp target/helm/kubernetes/frp --wait