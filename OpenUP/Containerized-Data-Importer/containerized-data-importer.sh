#export TAG=$(curl -s -w %{redirect_url} https://github.com/kubevirt/containerized-data-importer/releases/latest)
#export VERSION=$(echo ${TAG##*/})
#kubectl create -f https://github.com/kubevirt/containerized-data-importer/releases/download/v1.66.0/cdi-operator.yaml
#kubectl create -f https://github.com/kubevirt/containerized-data-importer/releases/download/v1.66.0/cdi-cr.yaml
kubectl create -f cdi-operator.yaml
kubectl create -f cdi-cr.yaml
kubectl wait -n cdi -l name=cdi-operator pod --for condition=Ready --timeout=1200s
kubectl wait -n cdi -l app=containerized-data-importer pod --for condition=Ready --timeout=1200s