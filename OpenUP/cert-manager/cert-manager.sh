. ../env.sh ../
VERSION=v1.21.3
../registry.sh quay.io "jetstack/cert-manager-controller:$VERSION"
../registry.sh quay.io "jetstack/cert-manager-webhook:$VERSION"
../registry.sh quay.io "jetstack/cert-manager-cainjector:$VERSION"
../registry.sh quay.io "jetstack/cert-manager-acmesolver:$VERSION"
../registry.sh quay.io "jetstack/cert-manager-startupapicheck:$VERSION"
$helm repo add jetstack https://charts.jetstack.io --force-update
$helm upgrade --install --namespace cert-manager --create-namespace cert-manager jetstack/cert-manager --version $VERSION -f values-cert-manager.yaml --wait --timeout 20m \
	--set "imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY"
$kubectl wait pod -l app.kubernetes.io/component=webhook --for=condition=Ready -n cert-manager