# AGENTS.md

This repository is a Kubernetes/Helm application specifically designed for deploying and managing AI models. It operates through orchestration and configuration rather than traditional application code.

### Investigation & Workflow
The primary workflow is managed by `kubeai.sh`.
1.  **Initialization:** Run `kubeai.sh` to setup the Helm repository (`https://www.kubeai.org`).
2.  **Secret Setup:** Ensure the HuggingFace token is set:
    ```bash
    kubectl create secret generic huggingface --from-literal=token='...'
    ```
3.  **Storage:** Persistent storage must be provisioned via `pvc.yaml`.
4.  **Deployment:** The core service is deployed using `values.yaml`, and individual models are configured and deployed using the `models/values.yaml` overrides.

### Critical Operational Notes
*   **Deployment Paradigm:** The application relies on Helm and `kubectl` for all operational setup.
*   **Model Updates:** Model swapping is achieved by modifying, not by modifying source code. Changes are driven by updating `models/values.yaml`.
*   **Command Reliability:** Always use `kubeai.sh` as it automates the dependency chain (repo update, secret creation, deployment).