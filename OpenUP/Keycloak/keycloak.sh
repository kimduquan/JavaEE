kubectl delete secret keycloak-admin
kubectl create secret generic keycloak-admin --from-literal=CLIENT_SECRET='ZUfIVP50PoA42CMfuRz29LCXtSAcODse' --from-literal=CLIENT_ID='admin-cli'
helm install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak -f values-keycloak.yaml
kubectl apply -f proxy.yaml