. ../env.sh
helm upgrade --install --wait llm-d oci://ghcr.io/llm-d/llm-d-deployer/llm-d --version 1.0.23 -f values-llm-d.yaml