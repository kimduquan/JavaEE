. ../env.sh
IMAGE_TAG=37.0.0-debian-12-r0
../registry.sh docker.io "bitnamilegacy/wildfly:$IMAGE_TAG"
$kubectl --kubeconfig="${KUBECONFIG}" --namespace $NAMESPACE delete secret oidc
$kubectl --kubeconfig="${KUBECONFIG}" --namespace $NAMESPACE create secret generic oidc --from-literal=OIDC_CLIENT_SECRET='cIkzKJZ4A7jXKpXfaxC4GaLxJAmmEqhP' --from-literal=OIDC_CLIENT_ID='account'
$kubectl --kubeconfig="${KUBECONFIG}" --namespace $NAMESPACE delete cm oidc
OIDC_PROVIDER_URI="https://${DOMAIN}/auth/realms/EPF-dev/.well-known/openid-configuration"
$kubectl --kubeconfig="${KUBECONFIG}" --namespace $NAMESPACE create cm oidc --from-literal=OIDC_PROVIDER_URI='${OIDC_PROVIDER_URI}'
$helm --kubeconfig="${KUBECONFIG}" --namespace $NAMESPACE upgrade --install wildfly oci://registry-1.docker.io/bitnamicharts/wildfly -f values-wildfly.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.tag=$IMAGE_TAG"
	--set "ingress.hostname=$PRODUCTION_DOMAIN"
	--set "ingress.extraHosts[0].name=$STAGING_DOMAIN"
	--set "ingress.extraHosts[1].name=$DEV_DOMAIN"
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect '/subsystem=undertow/application-security-domain=other:write-attribute(name=integrated-jaspi, value=false)'
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect '/subsystem=undertow/server=default-server/host=default-host/location="\/":remove()'
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect '/subsystem=undertow/configuration=handler/file=welcome-content:remove()'
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect ':reload'
#kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/application-security-domain=other:write-attribute(name=integrated-jaspi, value=false)'
#kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/server=default-server/host=default-host/location="\/":remove()'
#kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/configuration=handler/file=welcome-content:remove()'
#kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command=':reload'