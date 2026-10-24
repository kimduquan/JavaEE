. ../env.sh
. env.sh
../registry.sh docker.io "bitnamilegacy/keycloak:26.3.3-debian-12-r0"
#$kubectl --kubeconfig=$KUBECONFIG delete secret keycloak-admin --wait
$kubectl --kubeconfig=$KUBECONFIG create secret generic keycloak-admin --from-literal=CLIENT_SECRET='y1wf2SyO6p8eZq65KbvbNB1gE0cEYyMx' --from-literal=CLIENT_ID='admin-cli' --from-literal=PASSWORD='090323508'
#$kubectl --kubeconfig=$KUBECONFIG delete secret keycloak --wait
$kubectl --kubeconfig=$KUBECONFIG create secret generic keycloak --from-literal=DATABASE_USER='keycloak.keycloak' --from-literal=DATABASE_PASSWORD='090323508'
$helm --kubeconfig=$KUBECONFIG upgrade --install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak --version 25.2.0 -f values-keycloak.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "ingress.hostname=$MANAGEMENT_DOMAIN" \
	--set "ingress.extraRules[0].host=$PRODUCTION_DOMAIN" \
	--set "ingress.extraRules[1].host=$STAGING_DOMAIN" \
	--set "ingress.extraRules[2].host=$DEV_DOMAIN" \