kubectl delete secret oidc
kubectl create secret generic oidc --from-literal=OIDC_CLIENT_SECRET='cIkzKJZ4A7jXKpXfaxC4GaLxJAmmEqhP' --from-literal=OIDC_CLIENT_ID='account'
kubectl delete cm oidc
kubectl create cm oidc --from-literal=OIDC_PROVIDER_URI='https://chipmunk-capable-prawn.ngrok-free.app/auth/realms/EPF-dev/.well-known/openid-configuration'
helm install wildfly oci://registry-1.docker.io/bitnamicharts/wildfly -f values-wildfly.yaml --wait
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect '/subsystem=undertow/application-security-domain=other:write-attribute(name=integrated-jaspi, value=false)'
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect '/subsystem=undertow/server=default-server/host=default-host/location="\/":remove()'
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect '/subsystem=undertow/configuration=handler/file=welcome-content:remove()'
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect ':reload'
#kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/application-security-domain=other:write-attribute(name=integrated-jaspi, value=false)'
#kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/server=default-server/host=default-host/location="\/":remove()'
#kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/configuration=handler/file=welcome-content:remove()'
#kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command=':reload'