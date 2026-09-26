. ../../env.sh
IMAGE_TAG=v1.20.2
CERTGEN_IMAGE_TAG=v0.4.11
ENVOY_IMAGE_TAG=v1.37.6-1789133542-cbec91f666af0bf742da986d43832932dbb26b82
OPERATOR_IMAGE_TAG=v1.20.2
NODEINIT_IMAGE_TAG=1782916218-36ae25f
CLUSTERMESH_APISERVER_IMAGE_TAG=v1.20.2
../../registry.sh quay.io "cilium/cilium:$IMAGE_TAG"
../../registry.sh quay.io "cilium/certgen:$CERTGEN_IMAGE_TAG"
../../registry.sh quay.io "cilium/cilium-envoy:$ENVOY_IMAGE_TAG"
../../registry.sh quay.io "cilium/operator:$OPERATOR_IMAGE_TAG"
../../registry.sh quay.io "cilium/operator-generic:$OPERATOR_IMAGE_TAG"
../../registry.sh quay.io "cilium/startup-script:$NODEINIT_IMAGE_TAG"
../../registry.sh quay.io "cilium/clustermesh-apiserver:$CLUSTERMESH_APISERVER_IMAGE_TAG"
helm repo add cilium https://helm.cilium.io/
helm --kubeconfig="${EPF_CLUSTER_KUBE_CONFIG}" upgrade --install --wait --timeout 40m cilium cilium/cilium --version 1.20.2 --namespace=kube-system -f values-cilium.yaml \
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
	--set "clustermesh.apiserver.image.tag=$CLUSTERMESH_APISERVER_IMAGE_TAG"
#	--set "k8sServiceHost=172.23.225.250" \
#	--set "k8sServicePort=30443"