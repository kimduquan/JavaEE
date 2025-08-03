kubectl delete secret supavisor
kubectl create secret generic supavisor --from-literal=SECRET_KEY_BASE="$(openssl rand -hex 32)" --from-literal=API_JWT_SECRET="$(openssl rand -hex 32)" --from-literal=METRICS_JWT_SECRET="$(openssl rand -hex 32)" --from-literal=VAULT_ENC_KEY="$(openssl rand -hex 16)"
kubectl apply -f kubernetes.yml
kubectl wait deployment --for condition=available --timeout=60s supavisor
kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=supavisor
#psql postgres://epf.epf:Password1234@supavisor.default.svc.cluster.local:5452/epf?sslmode=disable