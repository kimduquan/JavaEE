# AGENTS.md

This repository is an infrastructure-oriented Kubernetes/Helm application for deploying and managing AI models. Agents should focus on configuration changes, not source code logic.

### Core Workflow (Executed via `kubeai.sh`)
1.  **Initialize:** Run `kubeai.sh` to set up the Helm repository and dependencies.
2.  **Provision Storage:** Apply persistent storage using `kubectl apply -f pvc.yaml`.
3.  **Base Deployment:** Deploy the core service via `helm upgrade --install kubeai kubeai/kubeai -f values.yaml`.
4.  **Model Updates:** Model swapping is done by modifying `models/values.yaml` overrides and re-running the deployment command.

### Architectural Quirks
*   **Data-Driven Model Swap**: New AI models are added or swapped purely by updating the `models/values.yaml` files, not by modifying application source code.
*   **Configuration Hierarchy**: 
    *   `values.yaml`: Main Helm chart defaults.
    *   `models/values.yaml`: Specific overrides for individual models.
*   **Execution Context**: All primary tasks are executed against Kubernetes manifests and Helm configurations, not a standard application entry point or test suite (no standard `lint`/`test` commands found).

### Key Constraints
*   Rely exclusively on Kubernetes manifests and Helm commands for operational tasks.
*   Maintain configuration in `values.yaml` files to update infrastructure.
*   Verify changes via `kubectl describe` or `helm get values`.