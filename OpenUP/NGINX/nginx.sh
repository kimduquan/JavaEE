. ../env.sh ../
../registry.sh docker.io "bitnamilegacy/nginx:1.29.1-debian-12-r0"
../registry.sh docker.io "bitnamilegacy/git:2.51.0-debian-12-r0"
../registry.sh docker.io "bitnamilegacy/nginx-exporter:1.4.2-debian-12-r9"
../secret.sh git-ssh
$helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install nginx oci://registry-1.docker.io/bitnamicharts/nginx --version 25.2.14 -f values-nginx.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "global.security.allowInsecureImages=true" \
	--set "ingress.hostname=$PRODUCTION_DOMAIN" \
	--set "ingress.extraRules[0].host=$STAGING_DOMAIN" \
	--set "ingress.extraRules[1].host=$DEV_DOMAIN" \
	--set "httpRoute.hostnames[0]=$PRODUCTION_DOMAIN" \
	--set "httpRoute.hostnames[1]=$STAGING_DOMAIN" \
	--set "httpRoute.hostnames[2]=$DEV_DOMAIN"
$kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f scaled-object.yaml
#kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f elasti-service.yaml
