kubectl delete secret oidc
kubectl create secret generic oidc --from-literal=oidc.client.secret='6lbr0kcDPXWu8tHrcNl190QxL9NHRMVO'
kubectl delete cm oidc
kubectl create cm oidc --from-literal=oidc_provider_uri='http://host.docker.internal/openid/realms/EPF/.well-known/openid-configuration' --from-literal=oidc_client_id='oidc-client'
helm install wildfly oci://registry-1.docker.io/bitnamicharts/wildfly -f wildfly_new.yaml