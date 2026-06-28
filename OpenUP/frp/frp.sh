. ../env.sh
kubectl delete configmap frp
kubectl create configmap frp --from-file=frps=frps.toml
ssh-keygen -q -t ed25519 -N "" -f id_ed25519
kubectl create secret generic frp-ssh --type=kubernetes.io/ssh-auth --from-file=ssh-privatekey=id_ed25519
kubectl create secret generic frp-ssh-auth --from-file=authorized_keys=~/.ssh/id_ed25519.pub
kubectl delete -f tls.yaml
kubectl apply -f tls.yaml
kubectl delete -f ingress.yml
docker build --file $FRP_SOURCE_DIR/dockerfiles/Dockerfile-for-frps --tag epf/frp:1.0.0 $FRP_SOURCE_DIR
mvn clean package -U
helm upgrade --install frp target/helm/kubernetes/frp --wait
#kubectl apply -f ingress.yml
kubectl get secret frp-client-tls -o jsonpath="{.data['tls\.crt']}" | base64 -d > frp-client-tls.crt
kubectl get secret frp-client-tls -o jsonpath="{.data['tls\.key']}" | base64 -d > frp-client-tls.key
kubectl get secret frp-client-tls -o jsonpath="{.data['ca\.crt']}" | base64 -d > frp-client-ca.crt