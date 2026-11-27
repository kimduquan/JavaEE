. ../env.sh ../
$helm repo add metrics-server https://kubernetes-sigs.github.io/metrics-server/ --force-update
$helm upgrade --install metrics-server metrics-server/metrics-server --version 3.14.0 -f values-metrics-server.yaml --wait --timeout 20m