# 🤖 Project Overview
This repository is a starter template for building AI agents using CopilotKit and LangGraph, integrated into a Next.js application.

## 🚀 Setup and Execution

*   **Prerequisites**: Node.js 18+, Python 3.8+, OpenAI API Key.
*   **Recommended Installation**: Use `pnpm` for package management.
*   **Core Commands**:
    *   Start development (UI + Agent): `pnpm dev`
    *   Install frontend dependencies: `pnpm install`
    *   Install agent Python dependencies: `pnpm install:agent`
    *   Run lint checks: `pnpm lint`

## ⚙️ Architecture and Workflow

*   **Frontend**: Next.js (TypeScript), handling UI and CopilotKit integration.
*   **Agent Backend**: LangGraph/Python. The agent runs as a separate service on **port 8000**.
*   **Workflow Requirement**: To ensure proper operation, both the Next.js UI and the LangGraph agent must be running concurrently.
*   **Entry Points**:
    *   UI: `src/app/page.tsx`
    *   Agent Server: LangGraph implementation (check `agent` directory for entry points)

## 🚨 Operational Gotchas

*   **Agent Connection**: If the UI fails to connect to tools, verify that the LangGraph agent is running on port 8000 and that the `OPENAI_API_KEY` is correctly set.
*   **Dependency Management**: The project supports multiple package managers (pnpm, npm, yarn, bun), but `pnpm` is recommended. Ensure you generate your own lockfile and commit it.