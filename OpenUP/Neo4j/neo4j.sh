kubectl create secret generic neo4j --from-literal=password='090323508'
helm install neo4j oci://registry-1.docker.io/bitnamicharts/neo4j -f values-neo4j.yaml --wait