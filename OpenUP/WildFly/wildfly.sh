kubectl delete secret oidc
kubectl create secret generic oidc --from-literal=OIDC_CLIENT_SECRET='4GFBcFZ0lrJVHUBWz2nGh1w5VWhOXePV'
kubectl delete cm oidc
kubectl create cm oidc --from-literal=OIDC_PROVIDER_URI='https://touching-pika-enabled.ngrok-free.app/realms/EPF-dev/.well-known/openid-configuration' --from-literal=OIDC_CLIENT_ID='account'
kubectl delete cm wildfly-custom-headers
kubectl create cm wildfly-custom-headers --from-literal=ngrok-skip-browser-warning=true
helm install wildfly oci://registry-1.docker.io/bitnamicharts/wildfly -f values-wildfly.yaml
#/opt/bitnami/wildfly/bin/jboss-cli.sh --connect --controller=127.0.0.1:9990 --command=reload