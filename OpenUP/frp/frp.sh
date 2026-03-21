. ../env.sh
docker build --file $FRP_SOURCE_DIR/dockerfiles/Dockerfile-for-frps --tag frps $FRP_SOURCE_DIR
mvn clean package -U