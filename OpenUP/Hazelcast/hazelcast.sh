helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm repo update
helm install hazelcast hazelcast/hazelcast -f hazelcast.yaml