. ../Cilium/env.sh
sudo snap info k8s
sudo snap refresh k8s
sudo snap install k8s --classic --channel=1.35-classic/stable
sudo k8s bootstrap --file bootstrap-config.yaml --timeout 5m
sudo k8s helm upgrade --install --wait --timeout 40m cilium cilium/cilium --version $CILIUM_CHART_VERSION --namespace=kube-system -f ../Cilium/values-cilium.yaml \
	--set "k8sServiceHost=127.0.0.1" \
	--set "k8sServicePort=6443" \
	--set "cluster.name=epf-management" \
	--set "cluster.id=2" \
	--set "agentNotReadyTaintKey=node.cilium.io/agent-not-ready" \
	--set "clustermesh.mcsapi.corednsAutoConfigure.coredns.configMapName=ck-dns-coredns"
sudo k8s status --wait-ready --timeout 5m
sudo snap install kubectl --classic
sudo k8s config > ~/.kube/config