#export TAG=$(curl -s -w %{redirect_url} https://github.com/kubevirt/containerized-data-importer/releases/latest)
#export VERSION=$(echo ${TAG##*/})
#kubectl create -f https://github.com/kubevirt/containerized-data-importer/releases/download/v1.66.1/cdi-operator.yaml
#kubectl create -f https://github.com/kubevirt/containerized-data-importer/releases/download/v1.66.1/cdi-cr.yaml
kubectl create -f cdi-operator.yaml
kubectl create -f cdi-cr.yaml
kubectl wait -n cdi -l app=containerized-data-importer deployment --for condition=Available --timeout=1200s
kubectl wait -n cdi -l name=cdi-operator deployment --for condition=Available --timeout=1200s