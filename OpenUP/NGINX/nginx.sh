ssh-keygen -q -t ed25519 -N "" -C "gitea_admin" -f id_ed25519
kubectl create secret generic git-ssh --type=kubernetes.io/ssh-auth --from-file=ssh-privatekey=id_ed25519 --from-file=ssh-publickey=id_ed25519.pub
helm upgrade --install nginx oci://registry-1.docker.io/bitnamicharts/nginx --version 25.2.14 -f values.yaml --wait