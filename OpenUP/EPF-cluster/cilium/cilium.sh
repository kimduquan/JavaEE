. ../../env.sh
IMAGE_TAG=v1.20.1
CERTGEN_IMAGE_TAG=v0.4.9
ENVOY_IMAGE_TAG=v1.37.5-1786810558-766ccfb37260a43e9d228837aa84ce3faf9f64e7
OPERATOR_IMAGE_TAG=v1.20.1
NODEINIT_IMAGE_TAG=1782916218-36ae25f
CLUSTERMESH_APISERVER_IMAGE_TAG=v1.20.1
../../registry.sh quay.io "cilium/cilium:$IMAGE_TAG"
../../registry.sh quay.io "cilium/certgen:$CERTGEN_IMAGE_TAG"
../../registry.sh quay.io "cilium/cilium-envoy:$ENVOY_IMAGE_TAG"
../../registry.sh quay.io "cilium/operator:$OPERATOR_IMAGE_TAG"
../../registry.sh quay.io "cilium/operator-generic:$OPERATOR_IMAGE_TAG"
../../registry.sh quay.io "cilium/startup-script:$NODEINIT_IMAGE_TAG"
../../registry.sh quay.io "cilium/clustermesh-apiserver:$CLUSTERMESH_APISERVER_IMAGE_TAG"
helm repo add cilium https://helm.cilium.io/
helm --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" upgrade --install --wait --timeout 40m cilium cilium/cilium --version 1.20.1 --namespace=kube-system -f values-cilium.yaml \
	--set "image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/cilium" \
	--set "image.tag=$IMAGE_TAG" \
	--set "certgen.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/certgen" \
	--set "certgen.image.tag=$CERTGEN_IMAGE_TAG" \
	--set "envoy.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/cilium-envoy" \
	--set "envoy.image.tag=$ENVOY_IMAGE_TAG" \
	--set "operator.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/operator" \
	--set "operator.image.tag=$OPERATOR_IMAGE_TAG" \
	--set "nodeinit.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/startup-script" \
	--set "nodeinit.image.tag=$NODEINIT_IMAGE_TAG" \
	--set "clustermesh.apiserver.image.repository=$EPF_CLUSTER_IMAGE_REGISTRY/cilium/clustermesh-apiserver" \
	--set "clustermesh.apiserver.image.tag=$CLUSTERMESH_APISERVER_IMAGE_TAG" \