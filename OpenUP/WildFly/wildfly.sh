kubectl delete secret oidc
kubectl create secret generic oidc --from-literal=OIDC_CLIENT_SECRET='4GFBcFZ0lrJVHUBWz2nGh1w5VWhOXePV'
kubectl delete cm oidc
kubectl create cm oidc --from-literal=OIDC_PROVIDER_URI='http://host.docker.internal/realms/EPF-dev/.well-known/openid-configuration' --from-literal=OIDC_CLIENT_ID='account'
helm install wildfly oci://registry-1.docker.io/bitnamicharts/wildfly -f values-wildfly.yaml