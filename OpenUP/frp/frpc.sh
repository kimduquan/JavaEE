. ../env.sh
git submodule update --init --recursive
sudo docker build --file frp/dockerfiles/Dockerfile-for-frpc --tag epf/frpc:1.0.0 frp
../registry.sh docker.io "epf/frpc:1.0.0"