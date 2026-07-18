kubectl create configmap initdb --from-file=initdb.sql
helm install postgresql oci://registry-1.docker.io/bitnamicharts/postgresql -f values-postgresql.yaml --wait