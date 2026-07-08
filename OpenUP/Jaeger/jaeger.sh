helm repo add jaegertracing https://jaegertracing.github.io/helm-charts --force-update
helm install jaeger jaegertracing/jaeger -f values.yaml --wait