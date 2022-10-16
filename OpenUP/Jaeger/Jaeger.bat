call kubectl create -f https://github.com/jaegertracing/jaeger-operator/releases/download/v1.38.0/jaeger-operator.yaml -n observability
call kubectl get deployment jaeger-operator -n observability
call kubectl apply -f Jaeger.yaml