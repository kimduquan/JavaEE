kubectl create configmap initdb --from-file=initdb.sql
kubectl delete pvc/data-postgresql-primary-0
kubectl delete pvc/data-postgresql-read-0
helm install postgresql oci://registry-1.docker.io/bitnamicharts/postgresql -f values-postgresql.yaml --wait