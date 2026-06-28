kubectl create namespace kserve
kubectl apply --server-side --namespace kserve --wait -f https://github.com/kserve/kserve/releases/download/v0.19.0/kserve.yaml