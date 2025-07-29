#helm repo add secrets-store-csi-driver https://kubernetes-sigs.github.io/secrets-store-csi-driver/charts
#helm install csi-secrets-store secrets-store-csi-driver/secrets-store-csi-driver
helm install vault oci://registry-1.docker.io/bitnamicharts/vault -f values-vault.yaml
#kubectl exec vault-server-0 -- vault operator init
#kubectl exec vault-server-0 -i -t -- vault operator unseal