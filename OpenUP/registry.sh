#. env.sh
REPOSITORY="$1"
IMAGE="$2"
if [ "$K8S_ENV" = "microk8s" ]; then
    sudo microk8s ctr image pull --all-platforms "$REPOSITORY/$IMAGE"
    sudo microk8s ctr image tag "$REPOSITORY/$IMAGE" "$EPF_CLUSTER_IMAGE_REGISTRY/$IMAGE"
    sudo microk8s ctr image push --plain-http "$EPF_CLUSTER_IMAGE_REGISTRY/$IMAGE"
else
    sudo docker pull "$REPOSITORY/$IMAGE"
    sudo docker tag "$REPOSITORY/$IMAGE" "$EPF_CLUSTER_IMAGE_REGISTRY/$IMAGE"
    sudo docker push "$EPF_CLUSTER_IMAGE_REGISTRY/$IMAGE"
fi