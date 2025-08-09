helm install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak -f values-keycloak.yaml
kubectl apply -f proxy.yaml