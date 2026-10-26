SECRET="$1"
SECRET_DIR="~/.secret"
SECRET_FILE="$SECRET_DIR/$SECRET.yaml"
export DEFAULT_PASSWORD=
if [ -z "$SECRET" ]; then
else
	if [ -f "$SECRET_FILE" ]; then
	elif [ "$SECRET" = "epf-cluster" ]; then
		kubectl create secret generic epf-cluster \
			--from-file=value=${EPF_CLUSTER_KUBE_CONFIG} \
			--dry-run=client -o yaml > "$SECRET_FILE"
		kubectl apply -f "$SECRET_FILE"
	elif [ "$SECRET" = "keycloak-admin" ]; then
		kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic keycloak-admin \
			--from-literal=CLIENT_SECRET='' \
			--from-literal=CLIENT_ID='admin-cli' \
			--from-literal=PASSWORD='$DEFAULT_PASSWORD' \
			--dry-run=client -o yaml > "$SECRET_FILE"
		kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f "$SECRET_FILE"
	elif [ "$SECRET" = "keycloak-db" ]; then
		kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic keycloak-db \
			--from-literal=DATABASE_USER='keycloak.keycloak' 
			--from-literal=DATABASE_PASSWORD='$DEFAULT_PASSWORD' \
			--dry-run=client -o yaml > "$SECRET_FILE"
		kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f "$SECRET_FILE"
	elif [ "$SECRET" = "postgresql" ]; then
		kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic postgresql \
			--from-literal=postgres-password='$DEFAULT_PASSWORD' 
			--from-literal=password='$DEFAULT_PASSWORD' \
			--from-literal=replication-password='$DEFAULT_PASSWORD' \
			--dry-run=client -o yaml > "$SECRET_FILE"
		kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f "$SECRET_FILE"
	elif echo "$SECRET" | grep -q "\."; then
		envsubst < "$SECRET" > "$SECRET.tmp"
		mv "$SECRET.tmp" "$SECRET"
	elif [ "$SECRET" = "supavisor" ]; then
		
		#kubectl create secret generic supavisor --from-literal=SECRET_KEY_BASE="$(openssl rand -hex 32)" --from-literal=API_JWT_SECRET="$(openssl rand -hex 32)" --from-literal=METRICS_JWT_SECRET="$(openssl rand -hex 32)" --from-literal=VAULT_ENC_KEY="$(openssl rand -hex 16)"
		kubectl create secret generic supavisor --from-literal=SECRET_KEY_BASE="dc9b087847b1028cd666cc99b7d1755cbda89558d6c8c349202dab51a01d00bb" --from-literal=API_JWT_SECRET="983cbf9422aba2dd9239e076497f5aaf9ee5acd8a5d939dc3b82ba661ea130d9" --from-literal=METRICS_JWT_SECRET="1e8ab580d84b238266233ce08d0846aafcfd7ae2fc140472b8014f296b5384e0" --from-literal=VAULT_ENC_KEY="3b1f4dad7cc7f6151359b1072152650f"
	fi
fi