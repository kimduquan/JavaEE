kubectl apply -f tcp.yaml
helm install haproxy oci://ghcr.io/haproxytech/helm-charts/kubernetes-ingress -f values.yaml --wait