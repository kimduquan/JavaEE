. ../env.sh
. env.sh
../registry.sh docker.io "bitnamilegacy/keycloak:26.3.3-debian-12-r0"
../registry.sh docker.io "bitnamilegacy/keycloak-config-cli:6.4.0-debian-12-r11"
./keycloak-config-cli.sh
KEYCLOAK_CLI_IMAGE_REPOSITORY="bitnami/keycloak-config-cli"
KEYCLOAK_CLI_IMAGE_TAG="6.5.0-debian-12-r0"
../registry.sh "localhost:32000" "$KEYCLOAK_CLI_IMAGE_REPOSITORY:$KEYCLOAK_CLI_IMAGE_TAG"
if [ -z "$DEPLOYMENT" ] || [ "$DEPLOYMENT" = "dev" ]; then
    MANAGEMENT_IP=$(kubectl --kubeconfig=$KUBECONFIG get svc -A --no-headers --field-selector spec.type=LoadBalancer,metadata.name!=epf-cluster-lb | awk '{print $5}')
    MANAGEMENT_DOMAIN=$(echo "$MANAGEMENT_IP" | tr '.' '-')
    MANAGEMENT_DOMAIN="$MANAGEMENT_DOMAIN.nip.io"
fi
../secret.sh keycloak-admin
../secret.sh keycloak-db
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG delete configmap keycloak-realms --wait
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create configmap keycloak-realms --from-file=KDQ-dev.json=realm-export.dev.json --from-file=KDQ.json=realm-export.prod.json --from-file=KDQ-stage.json=realm-export.stage.json
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak --version 25.2.0 -f values-keycloak.yaml --wait --timeout 30m \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "keycloakConfigCli.image.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "keycloakConfigCli.image.repository=$KEYCLOAK_CLI_IMAGE_REPOSITORY" \
	--set "keycloakConfigCli.image.tag=$KEYCLOAK_CLI_IMAGE_TAG" \
	--set "ingress.hostname=$MANAGEMENT_DOMAIN" \
	--set "ingress.extraRules[0].host=$PRODUCTION_DOMAIN" \
	--set "ingress.extraRules[1].host=$STAGING_DOMAIN" \
	--set "ingress.extraRules[2].host=$DEV_DOMAIN" \
	--set "auth.adminUser=$KEYCLOAK_ADMIN_USER"