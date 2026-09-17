NODE_IP=$(kubectl get node epf-node -o wide --no-headers | awk '{print $6}')
kubectl label node epf-node node-role.kubernetes.io/worker=""