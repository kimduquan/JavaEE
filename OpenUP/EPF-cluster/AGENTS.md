## Infrastructure and Operational Context

This repository contains Kubernetes manifests and operational scripts for setting up and managing the `epf-cluster`.

### High-Signal Facts
- **Cluster Scope:** This repository is focused on the `epf-cluster` namespace/infrastructure. Use the provided manifests (`epf-cluster.yaml`, `epf-cluster-v1beta2.yaml`) for cluster definitions and API interactions.
- **Setup/Bootstrap Protocol:** Initialization requires running `setup.sh`. This script deploys essential components like `metrics-server` and executes KEDA setup steps.
- **KEDA Management:** KEDA is managed within its own directory and requires execution of `./keda.sh` after the main `setup.sh` execution.
- **Key CLI Commands:**
    - `bash setup.sh`: Executes cluster setup scripts.
    - `cd KEDA && bash keda.sh`: Configures and starts KEDA components.
- **Infrastructure Quirks:** The cluster uses custom resources (`cluster.x-k8s.io/v1beta1`, `infrastructure.cluster.x-k8s.io/v1alpha1`) and relies heavily on external services like `ceph-block` storage and `kubevirt` for VM management.

## Investigating and Validating Changes
- **Read Configuration First:** Always prioritize inspecting the root manifests (`epf-cluster.*.yaml`) and setup/operational scripts (`setup.sh`, `keda.sh`) over general code structure.
- **Validation:** Use `k8s` tools against the cluster resources defined in the repository for any verification steps.