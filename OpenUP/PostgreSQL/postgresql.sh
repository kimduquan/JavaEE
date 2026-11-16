. ../env.sh ../
#../registry.sh registry-1.docker.io "bitnami/postgresql:latest"
../secret.sh postgresql
../secret.sh initdb.sql
kubectl create configmap initdb --from-file=initdb.sql
#kubectl delete pvc/data-postgresql-primary-0 --wait
#kubectl delete pvc/data-postgresql-read-0 --wait
EPF_CLUSTER_IMAGE_REGISTRY="registry-1.docker.io"
$helm upgrade --install postgresql oci://registry-1.docker.io/bitnamicharts/postgresql --version 18.12.19 -f values-postgresql.yaml --wait --timeout 20m \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "global.defaultStorageClass=ceph-block" \
	--set "image.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "global.security.allowInsecureImages=true"
kubectl apply -f service-export.yaml