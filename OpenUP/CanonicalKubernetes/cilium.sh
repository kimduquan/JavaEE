. ../env.sh ../
. ../Cilium/env.sh
#../registry.sh quay.io "cilium/cilium:$CILIUM_IMAGE_TAG"
#../registry.sh quay.io "cilium/certgen:$CILIUM_CERTGEN_IMAGE_TAG"
#../registry.sh quay.io "cilium/cilium-envoy:$CILIUM_ENVOY_IMAGE_TAG"
#../registry.sh quay.io "cilium/operator:$CILIUM_OPERATOR_IMAGE_TAG"
#../registry.sh quay.io "cilium/operator-generic:$CILIUM_OPERATOR_IMAGE_TAG"
#../registry.sh quay.io "cilium/startup-script:$CILIUM_NODEINIT_IMAGE_TAG"
#../registry.sh quay.io "cilium/clustermesh-apiserver:$CILIUM_CLUSTERMESH_APISERVER_IMAGE_TAG"
EPF_CLUSTER_IMAGE_REGISTRY=quay.io
sudo k8s helm upgrade --install --wait --timeout 40m cilium cilium/cilium --version $CILIUM_CHART_VERSION --namespace=kube-system -f ../Cilium/values-cilium.yaml \
	--set "image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/cilium" \
	--set "image.tag=$CILIUM_IMAGE_TAG" \
	--set "certgen.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/certgen" \
	--set "certgen.image.tag=$CILIUM_CERTGEN_IMAGE_TAG" \
	--set "envoy.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/cilium-envoy" \
	--set "envoy.image.tag=$CILIUM_ENVOY_IMAGE_TAG" \
	--set "operator.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/operator" \
	--set "operator.image.tag=$CILIUM_OPERATOR_IMAGE_TAG" \
	--set "nodeinit.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/startup-script" \
	--set "nodeinit.image.tag=$CILIUM_NODEINIT_IMAGE_TAG" \
	--set "clustermesh.apiserver.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/clustermesh-apiserver" \
	--set "clustermesh.apiserver.image.tag=$CILIUM_CLUSTERMESH_APISERVER_IMAGE_TAG" \
	--set "k8sServiceHost=127.0.0.1" \
	--set "k8sServicePort=6443" \
	--set "cluster.name=epf-management" \
	--set "cluster.id=2" \
	--set "agentNotReadyTaintKey=node.cilium.io/agent-not-ready" \
	--set "clustermesh.mcsapi.corednsAutoConfigure.coredns.configMapName=ck-dns-coredns" \
	--set "tls.caBundle.enabled=$CILIUM_CLUSTERMESH_CONNECT" \
	--set "clustermesh.config.clusters.epf-cluster.enabled=$CILIUM_CLUSTERMESH_CONNECT" \
	--set "clustermesh.config.clusters.epf-management.enabled=$CILIUM_CLUSTERMESH_CONNECT" \
	--set "clustermesh.config.clusters.epf-cluster.ips[0]=$CLUSTERMESH_CONFIG_CLUSTERS_EPF_CLUSTER_IP" \
	--set "clustermesh.config.clusters.epf-management.ips[0]=$CLUSTERMESH_CONFIG_CLUSTERS_EPF_MANAGEMENT_IP"