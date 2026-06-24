ssh-keygen -q -t ed25519 -N "" -C "kimduquan03@gmail.com" -f id_ed25519
kubectl create secret generic git-ssh --type=kubernetes.io/ssh-auth --from-file=ssh-privatekey=id_ed25519 --from-file=ssh-publickey=id_ed25519.pub
helm install nginx oci://registry-1.docker.io/bitnamicharts/nginx -f values.yaml --wait