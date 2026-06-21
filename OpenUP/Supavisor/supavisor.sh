. ../env.sh
mvn clean install -U
kubectl delete secret supavisor
kubectl delete hpa supavisor
#kubectl create secret generic supavisor --from-literal=SECRET_KEY_BASE="$(openssl rand -hex 32)" --from-literal=API_JWT_SECRET="$(openssl rand -hex 32)" --from-literal=METRICS_JWT_SECRET="$(openssl rand -hex 32)" --from-literal=VAULT_ENC_KEY="$(openssl rand -hex 16)"
kubectl create secret generic supavisor --from-literal=SECRET_KEY_BASE="dc9b087847b1028cd666cc99b7d1755cbda89558d6c8c349202dab51a01d00bb" --from-literal=API_JWT_SECRET="983cbf9422aba2dd9239e076497f5aaf9ee5acd8a5d939dc3b82ba661ea130d9" --from-literal=METRICS_JWT_SECRET="1e8ab580d84b238266233ce08d0846aafcfd7ae2fc140472b8014f296b5384e0" --from-literal=VAULT_ENC_KEY="3b1f4dad7cc7f6151359b1072152650f"
#kubectl apply -f kubernetes.yml
#kubectl wait deployment --for condition=available --timeout=300s supavisor
#kubectl wait pod --for condition=ready --timeout=300s -l app.kubernetes.io/name=supavisor
kubectl apply -f tls.yml
helm upgrade --install supavisor target/helm/kubernetes/supavisor --wait
kubectl autoscale deployment supavisor --max 2