# AGENTS.md

# KubeVirt Repository Guidance

### Overview
This repository defines and manages the KubeVirt operator infrastructure, primarily through Kubernetes YAML manifests. The focus is on operator deployment and cluster integration rather than application logic in standard source code.

### Investigation Checklist
*   **Core Files:** Start with manifests like `kubevirt-operator.yaml` and `kubevirt-cr.yaml` to understand the desired state and resource definitions.
*   **Setup:** Check `kubevirt.sh` for local environment setup and required operational steps.
*   **Architecture:** The component boundaries are defined by Kubernetes resources (`Kind: ...` in YAML).
*   **Developer Commands:** No standard package manager (npm, pip, cargo) commands are visible. Only `kubevirt.sh` is available for local execution.

### Constraints
*   **Executable Truth:** Always trust the configuration and deployment manifests over implied structure.
*   **No Conventions:** No explicit coding style conventions exist for this repository.