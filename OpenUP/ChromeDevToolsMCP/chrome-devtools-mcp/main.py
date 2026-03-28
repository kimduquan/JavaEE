import asyncio
import os
from fastmcp import FastMCP
from fastmcp.server.server import create_proxy
from fastmcp.mcp_config import MCPConfig, StdioMCPServer

CONFIG = MCPConfig(mcpServers={"chrome-devtools": StdioMCPServer(command="npx", args=["-y", "chrome-devtools-mcp@latest"])})
APP = create_proxy(target=CONFIG)
HOSTNAME = os.getenv(key="HOSTNAME")
PORT = int(os.getenv(key="PORT"))

if __name__ == "__main__":
    asyncio.run(APP.run_http_async(show_banner=False, host=HOSTNAME, port=PORT))