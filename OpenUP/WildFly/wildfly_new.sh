kubectl delete secret oidc
kubectl create secret generic oidc --from-literal=OIDC_CLIENT_SECRET='6lbr0kcDPXWu8tHrcNl190QxL9NHRMVO'
kubectl delete cm oidc
kubectl create cm oidc --from-literal=OIDC_PROVIDER_URI='https://host.docker.internal/openid/realms/EPF/.well-known/openid-configuration' --from-literal=OIDC_CLIENT_ID='oidc-client'
helm install wildfly oci://registry-1.docker.io/bitnamicharts/wildfly -f wildfly_new.yaml