kubectl delete secret oidc
kubectl create secret generic oidc --from-literal=OIDC_CLIENT_SECRET='Ux3NYf3FPWGtFuCIgS7wmEquW8NHUTmX'
kubectl delete cm oidc
kubectl create cm oidc --from-literal=OIDC_PROVIDER_URI='http://host.docker.internal/openid/realms/EPF/.well-known/openid-configuration' --from-literal=OIDC_CLIENT_ID='oidc-client'
helm install wildfly oci://registry-1.docker.io/bitnamicharts/wildfly -f values-wildfly.yaml