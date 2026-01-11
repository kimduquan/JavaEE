kubectl delete secret keycloak-admin
kubectl create secret generic keycloak-admin --from-literal=CLIENT_SECRET='EE6SLGoYOs310QMYKaYAoWBrsYd8r0tq' --from-literal=CLIENT_ID='admin-cli'
helm install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak -f values-keycloak.yaml