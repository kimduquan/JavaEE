SECRET="$1"
SECRET_DIR="~/.secret"
SECRET_FILE="$SECRET_DIR/$SECRET.yaml"
if [ -z "$SECRET" ]; then
else
	if [ -f "$SECRET_FILE" ]; then
	elif [ "$SECRET" = "epf-cluster" ]
		$kubectl create secret generic ${SECRET} \
		--from-file=value=${EPF_CLUSTER_KUBE_CONFIG} \
		--dry-run=client -o yaml > "$SECRET_FILE"
		$kubectl apply -f "$SECRET_FILE"
	elif [ "$SECRET" = "keycloak-admin" ]
		$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic ${SECRET} \
		--from-literal=CLIENT_SECRET='' \
		--from-literal=CLIENT_ID='admin-cli' \
		--from-literal=PASSWORD='' \
		--dry-run=client -o yaml > "$SECRET_FILE"
		$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f "$SECRET_FILE"
	fi
fi