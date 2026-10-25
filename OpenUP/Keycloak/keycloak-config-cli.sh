. ../env.sh
git submodule update --init --recursive
cd bitnami/containers/bitnami/keycloak-config-cli/6/debian-12
sudo docker build --push --tag "localhost:32000/bitnami/keycloak-config-cli:6.5.0-debian-12-r0" .
cd ../../../../../../
