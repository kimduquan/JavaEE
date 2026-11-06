export USER_DIR=~/
export SOURCE_DIR=~/git/JavaEE/OpenUP
export ODOO_SOURCE_DIR=~/git/bitnami/containers/bitnami/odoo/20/debian-12
export JAVA_HOME=~/jdk-21.0.9+10
export MAVEN_HOME=~/apache-maven-3.9.15/bin
export PATH=$MAVEN_HOME:$PATH
export DOCKER_BUILDKIT=1
export EPF_CLUSTER_KUBE_CONFIG=/home/kimduquan/epf-cluster.kubeconfig
export EPF_CLUSTER_SSH_KEY=/home/kimduquan/epf-cluster-ssh-key
export EPF_CLUSTER_CERT=/home/kimduquan/localhost.direct.SS.crt
export EPF_CLUSTER_CERT_KEY=/home/kimduquan/localhost.direct.SS.key
export EPF_CLUSTER_IMAGE_REGISTRY=192.168.1.2:32000
export SECRET_DIR="/home/kimduquan/.secret"
ENV_DIR="$1"
if [ -z "$ENV_DIR" ]; then
	:
else
	. "${ENV_DIR}prod-env.sh"
	. "${ENV_DIR}stage-env.sh"
	. "${ENV_DIR}dev-env.sh"
fi
export MANAGEMENT_DOMAIN=
export REALM_NAME=$PRODUCTION_REALM_NAME
export DOMAIN=$DEV_DOMAIN
export NAMESPACE=$DEV_NAMESPACE
export GATEWAY_IP=$DEV_GATEWAY_IP
export ROOT_EMAIL=$PRODUCTION_ROOT_EMAIL
export EMAIL_PORT=587
export DEPLOYMENT_POSTFIX=$DEV_DEPLOYMENT_POSTFIX
export PUBLIC_IP=$DEV_PUBLIC_IP
export REDIS_DATABASE=0
if [ "$DEPLOYMENT" = "production" ]; then
    export PUBLIC_IP=$PRODUCTION_PUBLIC_IP
    export DOMAIN=$PRODUCTION_DOMAIN
    export NAMESPACE=$PRODUCTION_NAMESPACE
    export GATEWAY_IP=$PRODUCTION_GATEWAY_IP
    export DEPLOYMENT_POSTFIX=$PRODUCTION_DEPLOYMENT_POSTFIX
    export REDIS_DATABASE=$PRODUCTION_REDIS_DATABASE
elif [ "$DEPLOYMENT" = "staging" ]; then
    export PUBLIC_IP=$STAGING_PUBLIC_IP
    export DOMAIN=$STAGING_DOMAIN
    export NAMESPACE=$STAGING_NAMESPACE
    export GATEWAY_IP=$STAGING_GATEWAY_IP
    export DEPLOYMENT_POSTFIX=$STAGING_DEPLOYMENT_POSTFIX
    export REDIS_DATABASE=$STAGING_REDIS_DATABASE
fi
export kubectl="sudo microk8s kubectl"
export helm="sudo microk8s helm"