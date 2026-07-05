. ./env.sh
kubectl port-forward svc/wildfly 9990:9990 &
cd EPF-webapp-v2
mvn wildfly:deploy
kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/application-security-domain=other:write-attribute(name=integrated-jaspi, value=false)'
kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/server=default-server/host=default-host/location="\/":remove()'
kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command='/subsystem=undertow/configuration=handler/file=welcome-content:remove()'
kubectl exec $(kubectl get pods -l 'app.kubernetes.io/name=wildfly' -o jsonpath='{.items[0].metadata.name}') -c wildfly -- /opt/bitnami/wildfly/bin/jboss-cli.sh --connect --command=':reload'