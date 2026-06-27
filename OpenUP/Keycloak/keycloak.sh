kubectl delete secret keycloak-admin
kubectl create secret generic keycloak-admin --from-literal=CLIENT_SECRET='y1wf2SyO6p8eZq65KbvbNB1gE0cEYyMx' --from-literal=CLIENT_ID='admin-cli'
helm upgrade --install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak -f values-keycloak.yaml --wait