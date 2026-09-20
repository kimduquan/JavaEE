. env.sh
REPOSITORY=%1
IMAGE=%2
sudo microk8s ctr image pull $REPOSITORY/$IMAGE
sudo microk8s ctr image tag $EPF_CLUSTER_IMAGE_REGISTRY/$IMAGE