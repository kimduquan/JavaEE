kubectl delete cm keycloak-custom-headers
kubectl create cm keycloak-custom-headers --from-literal=ngrok-skip-browser-warning=true
helm install keycloak oci://registry-1.docker.io/bitnamicharts/keycloak -f values-keycloak.yaml