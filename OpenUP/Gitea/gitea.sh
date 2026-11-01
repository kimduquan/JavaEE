. ../env.sh
../secret.sh gitea-secret
helm repo add gitea-charts https://dl.gitea.com/charts/
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install gitea gitea-charts/gitea --version v12.7.0 --wait -f values.yaml