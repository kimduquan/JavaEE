#kubectl apply -f target/kubernetes/kubernetes.yml
#kubectl wait deployment --for condition=available epf-event
#kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-event
helm install epf-event target/helm/kubernetes/epf-event --wait