kubectl create secret generic nextauth --from-literal=NEXTAUTH_SECRET="$(openssl rand -hex 32)"
kubectl apply -f kubernetes.yml
kubectl wait deployment --for condition=available epf-agent-webapp
kubectl wait pod --for condition=ready -l app.kubernetes.io/name=epf-agent-webapp
kubectl autoscale deployment epf-agent-webapp --max 1