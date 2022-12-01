helm repo add hazelcast https://hazelcast-charts.s3.amazonaws.com/
helm repo update
::helm install hazelcast hazelcast/hazelcast --set cluster.memberCount=1 --set mancenter.enabled=false
helm install hazelcast hazelcast/hazelcast -f hazelcast.yaml
::kubectl apply -f https://raw.githubusercontent.com/hazelcast/hazelcast/master/kubernetes-rbac.yaml