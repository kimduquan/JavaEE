kubectl create namespace kserve
helm upgrade --install --wait kserve-crd oci://ghcr.io/kserve/charts/kserve-crd
helm upgrade --install --wait kserve oci://ghcr.io/kserve/charts/kserve-resources --set kserve.controller.deploymentMode=Standard --set kserve.controller.gateway.ingressGateway.className=haproxy