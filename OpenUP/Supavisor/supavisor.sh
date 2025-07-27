kubectl delete secret supavisor
kubectl create secret generic supavisor --from-literal=SECRET_KEY_BASE="$(openssl rand -base64 64)"