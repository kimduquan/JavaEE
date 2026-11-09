. ../env.sh ../
../registry.sh docker.io "bitnamilegacy/nginx:1.29.1-debian-12-r0"
../registry.sh docker.io "bitnamilegacy/git:2.51.0-debian-12-r0"
../registry.sh docker.io "bitnamilegacy/nginx-exporter:1.4.2-debian-12-r9"
#ssh-keygen -q -t ed25519 -N "" -C "gitea_admin" -f id_ed25519
#kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG delete secret git-ssh --wait
#kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG create secret generic git-ssh --type=kubernetes.io/ssh-auth --from-file=ssh-privatekey=id_ed25519 --from-file=ssh-publickey=id_ed25519.pub
helm --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG upgrade --install nginx oci://registry-1.docker.io/bitnamicharts/nginx --version 25.2.14 -f values-nginx.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "global.security.allowInsecureImages=true" \
	--set "ingress.hostname=$PRODUCTION_DOMAIN" \
	--set "ingress.extraHosts[0].name=$STAGING_DOMAIN" \
	--set "ingress.extraHosts[1].name=$DEV_DOMAIN" \
	--set "httpRoute.hostnames[0]=$PRODUCTION_DOMAIN" \
	--set "httpRoute.hostnames[1]=$STAGING_DOMAIN" \
	--set "httpRoute.hostnames[2]=$DEV_DOMAIN"
#kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG -n keda apply -f reference-grant.yaml
#envsubst < interceptor-route.yaml | kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f -
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f scaled-object.yaml
kubectl --kubeconfig=$EPF_CLUSTER_KUBE_CONFIG apply -f elasti-service.yaml
