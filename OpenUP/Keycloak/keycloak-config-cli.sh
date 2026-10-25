. ../env.sh
git submodule update --init --recursive
cd bitnami/containers/bitnami/keycloak-config-cli/6/debian-12
sudo docker build --tag "bitnami/keycloak-config-cli:6.4.1-debian-12-r3" .
