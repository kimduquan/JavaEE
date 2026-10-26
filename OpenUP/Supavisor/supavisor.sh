. ../env.sh
../registry.sh ghcr.io "supabase/supavisor:2.9.13"
../secret.sh supavisor
mvn clean install -U
kubectl delete hpa supavisor
POSTGRES_PASSWORD=$(kubectl get secret postgresql -o jsonpath='{.data.postgres-password}' | base64 --decode)
#kubectl apply -f tls.yml
helm upgrade --install supavisor target/helm/kubernetes/supavisor --wait --timeout 40m \
	--set "app.image=$EPF_CLUSTER_IMAGE_REGISTRY/supabase/supavisor:2.9.13" \
	--set "app.envs.DATABASE_URL=ecto://postgres:$POSTGRES_PASSWORD@postgresql-primary.default.svc.clusterset.local:5432/postgres"
kubectl autoscale deployment supavisor --max 1
kubectl wait pod --for condition=ready --timeout=300s -l app.kubernetes.io/name=supavisor