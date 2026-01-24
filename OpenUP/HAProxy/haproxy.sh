#helm repo add haproxy-ingress https://haproxy-ingress.github.io/charts
#helm install haproxy-ingress haproxy-ingress/haproxy-ingress --namespace default --set controller.tcp.5452="default/supavisor:5452" --set controller.tcp.6543="default/supavisor:6543" --set controller.ingressClassResource.enabled=true --set controller.logs.enabled=true
#https://artifacthub.io/packages/helm/haproxytech/kubernetes-ingress
kubectl apply -f tcp.yaml
helm install haproxy oci://ghcr.io/haproxytech/helm-charts/kubernetes-ingress -f values.yaml --wait