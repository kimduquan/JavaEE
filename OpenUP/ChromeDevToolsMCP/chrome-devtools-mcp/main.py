import asyncio
import os
from fastmcp.server.server import create_proxy
from fastmcp.mcp_config import MCPConfig, StdioMCPServer
from opentelemetry.instrumentation import auto_instrumentation

auto_instrumentation.initialize()

LOG_LEVEL = os.environ.get("LOG_LEVEL", "debug")
CONFIG = MCPConfig(mcpServers={"chrome_devtools": StdioMCPServer(command="npx", args=["-y", "chrome-devtools-mcp@latest", "--no-performance-crux", "--no-usage-statistics"])})
APP = create_proxy(target=CONFIG)
HOSTNAME = os.environ.get("HOSTNAME", "0.0.0.0")
PORT = int(os.environ.get("PORT", "8002"))

if __name__ == "__main__":
    asyncio.run(APP.run_http_async(show_banner=False, transport="streamable-http", host=HOSTNAME, port=PORT, log_level=LOG_LEVEL, stateless_http=True, stateless=True))