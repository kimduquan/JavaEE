kubectl apply -f tcp.yaml
helm upgrade --install haproxy oci://ghcr.io/haproxytech/helm-charts/kubernetes-ingress -f values-kubernetes-ingress.yaml --wait