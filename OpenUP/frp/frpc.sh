. ../env.sh
git submodule update --init --recursive
sudo docker build --file frp/dockerfiles/Dockerfile-for-frpc --push --tag "localhost:32000/epf/frpc:1.0.0" frp