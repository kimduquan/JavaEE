helm install vault oci://registry-1.docker.io/bitnamicharts/vault -f values-vault.yaml
#kubectl exec vault-server-0 -- vault operator init
#kubectl exec vault-server-0 -i -t -- vault operator unseal