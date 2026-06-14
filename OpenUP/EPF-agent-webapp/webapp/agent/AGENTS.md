# 🤖 Project Overview: EPF Agent

This repository is a complex LangGraph/LangChain implementation of an AI Agent, integrated into a Next.js application. It functions as a service orchestrator, delegating tasks to specialized sub-agents via an MCP (MultiServerMCPClient).

## 🚀 Workflow and Architecture
*   **Core Component:** The agent is an orchestration layer (Supervisor Agent) that delegates tasks to specialized sub-agents/tools via MCP.
*   **Execution Environment:** The agent runs as an API server on port 8123, exposed via FastAPI.
*   **Dependencies:** Requires Python `>=3.12`. Recommended package manager: `pnpm`.

## 🛠️ Operational Commands
*   **Local Development:** `pnpm dev` starts both the UI and the LangGraph agent.
*   **Kubernetes Deployment/Management:**
    *   `./start.sh`: Deploys the agent using Kubernetes manifests (`kubectl apply -f kubernetes.yml`) and ensures proper scaling (`kubectl autoscale`).

## ⚠️ Operational Gotchas
*   **Environment Configuration:** The agent relies heavily on several critical environment variables to function, including:
    *   `OPENAI_API_KEY` / `OPENAI_BASE_URL` (for LLMs).
    *   `MCP_SERVER_URL` (for tool/agent communication).
    *   Redis configuration for persistence (`CHECKPOINTER_PERSISTENCE_URL_FORMAT`, `STORE_PERSISTENCE_URL_FORMAT`, `CACHE_PERSISTENCE_URL_FORMAT`).
    *   `JWT_KEY_URL` (for user authentication).
*   **Persistence:** Agent state (checkpoints) is persisted using Redis (`AsyncRedisSaver`).