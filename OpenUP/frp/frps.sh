. ../env.sh
git submodule update --init --recursive
sudo docker build --file frp/dockerfiles/Dockerfile-for-frps --tag epf/frps:1.0.0 frp