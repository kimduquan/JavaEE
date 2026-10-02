. ../env.sh
docker build --file $ODOO_SOURCE_DIR/Dockerfile --tag odoo $ODOO_SOURCE_DIR
../registry.sh docker.io "library/odoo:latest"
kubectl create secret generic smtp --from-literal=smtp-password=""
kubectl create secret generic odoo --from-literal=user="odoo.odoo" --from-literal=odoo-password="090323508" --from-literal=postgres-password="090323508"
helm upgrade --install odoo oci://registry-1.docker.io/bitnamicharts/odoo -f values-odoo.yaml --wait --timeout 20m \
	--set "image.registry=$EPF_CLUSTER_IMAGE_REGISTRY" \
	--set "image.repository=library/odoo" \
	--set "image.tag=latest" \
	--set "persistence.storageClass=ceph-rbd"
#helm install odoo oci://registry-1.docker.io/bitnamicharts/odoo -f values-odoo.yaml --wait --wait-for-jobs --set externalDatabase.host=postgresql-primary --set externalDatabase.port=5432 --set externalDatabase.postgresqlPostgresUser=postgres --set externalDatabase.database=odoo_template --set externalDatabase.create=true --set loadDemoData=false --set persistence.resourcePolicy=""