kubectl delete secret supavisor
kubectl create secret generic supavisor --from-literal=SECRET_KEY_BASE="$(openssl rand -base64 64)"
kubectl apply -f kubernetes.yml
kubectl wait deployment --for condition=available --timeout=60s supavisor
kubectl wait pod --for condition=ready --timeout=60s -l app.kubernetes.io/name=supavisor