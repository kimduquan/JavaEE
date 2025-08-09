kubectl delete secret oidc
kubectl create secret generic oidc --from-literal=OIDC_CLIENT_SECRET='kcG3e9FAbsiZmRzZydEQee3cZLmyci3W' --from-literal=OIDC_CLIENT_ID='account'
kubectl delete cm oidc
kubectl create cm oidc --from-literal=OIDC_PROVIDER_URI='https://keycloak.default.svc.cluster.local/auth/realms/EPF-dev/.well-known/openid-configuration'
helm install wildfly oci://registry-1.docker.io/bitnamicharts/wildfly -f values-wildfly.yaml
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect --controller=127.0.0.1:9990