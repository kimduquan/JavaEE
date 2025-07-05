kubectl delete configmap scylla
kubectl create cm scylla --from-file=scylla.yaml
helm install scylladb oci://registry-1.docker.io/bitnamicharts/scylladb -f values.yaml --set existingConfiguration=scylla