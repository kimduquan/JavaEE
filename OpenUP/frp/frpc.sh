. ../env.sh
git submodule update --init --recursive
sudo docker build --file frp/dockerfiles/Dockerfile-for-frpc --tag "$EPF_CLUSTER_IMAGE_REGISTRY/epf/frpc:1.0.0" frp
sudo docker push "$EPF_CLUSTER_IMAGE_REGISTRY/epf/frpc:1.0.0"