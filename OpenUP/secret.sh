SECRET="$1"
SECRET_DIR="~/.secret"
SECRET_FILE="$SECRET_DIR/$SECRET.yaml"
export DEFAULT_PASSWORD=
if [ -z "$SECRET" ]; then
else
	if [ -f "$SECRET_FILE" ]; then
	elif [ "$SECRET" = "epf-cluster" ]
		$kubectl create secret generic epf-cluster \
			--from-file=value=${EPF_CLUSTER_KUBE_CONFIG} \
			--dry-run=client -o yaml > "$SECRET_FILE"
		$kubectl apply -f "$SECRET_FILE"
	elif [ "$SECRET" = "keycloak-admin" ]
		$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic keycloak-admin \
			--from-literal=CLIENT_SECRET='' \
			--from-literal=CLIENT_ID='admin-cli' \
			--from-literal=PASSWORD='$DEFAULT_PASSWORD' \
			--dry-run=client -o yaml > "$SECRET_FILE"
		$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f "$SECRET_FILE"
	elif [ "$SECRET" = "keycloak-db" ]
		$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic keycloak-db \
			--from-literal=DATABASE_USER='keycloak.keycloak' 
			--from-literal=DATABASE_PASSWORD='$DEFAULT_PASSWORD' \
			--dry-run=client -o yaml > "$SECRET_FILE"
		$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f "$SECRET_FILE"
	elif [ "$SECRET" = "postgresql" ]
		$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic postgresql \
			--from-literal=postgres-password='$DEFAULT_PASSWORD' 
			--from-literal=password='$DEFAULT_PASSWORD' \
			--from-literal=replication-password='$DEFAULT_PASSWORD' \
			--dry-run=client -o yaml > "$SECRET_FILE"
		$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f "$SECRET_FILE"
	fi
fi