kubectl delete secret keycloak-admin
kubectl create secret generic keycloak-admin --from-literal=CLIENT_SECRET='qm04gKwlCKsJzkFMmpAoatgBvgxfE6A6' --from-literal=CLIENT_ID='admin-cli'
helm install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak -f values-keycloak.yaml --wait