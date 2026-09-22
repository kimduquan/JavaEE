. ../env.sh
../registry.sh registry-1.docker.io "bitnami/postgresql:latest"
kubectl create configmap initdb --from-file=initdb.sql
kubectl delete pvc/data-postgresql-primary-0
kubectl delete pvc/data-postgresql-read-0
helm upgrade --install postgresql oci://registry-1.docker.io/bitnamicharts/postgresql -f values-postgresql.yaml --wait \
	--set "global.imageRegistry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "global.defaultStorageClass=ceph-rbd" \
	--set "image.registry=$EPF_CLUSTER_IMAGE_REGISTRY"