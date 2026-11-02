. ../env.sh ../
SUPAVISOR_IMAGE="supabase/supavisor:2.9.13"
../registry.sh ghcr.io "$SUPAVISOR_IMAGE"
../secret.sh postgresql
../secret.sh supavisor
mvn clean install -U
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG delete hpa supavisor
POSTGRES_PASSWORD=$(kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG get secret postgresql -o jsonpath='{.data.postgres-password}' | base64 --decode)
#kubectl apply -f tls.yml
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install supavisor target/helm/kubernetes/supavisor --wait --timeout 40m \
	--set "app.image=$EPF_CLUSTER_IMAGE_REGISTRY/$SUPAVISOR_IMAGE" \
	--set "app.envs.DATABASE_URL=ecto://postgres:$POSTGRES_PASSWORD@postgresql-primary.default.svc.clusterset.local:5432/postgres"
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG autoscale deployment supavisor --max 2
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG wait pod --for condition=ready --timeout=300s -l app.kubernetes.io/name=supavisor