kubectl create namespace kserve
helm upgrade --install --wait kserve-crd oci://ghcr.io/kserve/charts/kserve-crd --version v0.18.0
helm upgrade --install --wait kserve oci://ghcr.io/kserve/charts/kserve-resources --version v0.18.0 --set kserve.controller.deploymentMode=Standard --set kserve.controller.gateway.ingressGateway.className=haproxy