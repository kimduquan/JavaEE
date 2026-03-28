from fastmcp import FastMCP
from fastmcp.server.server import create_proxy
from fastmcp.mcp_config import MCPConfig, StdioMCPServer

CONFIG = MCPConfig(mcpServers={"chrome-devtools": StdioMCPServer(command="npx", args=["-y", "chrome-devtools-mcp@latest"])})
APP = create_proxy(target=CONFIG)

if __name__ == "__main__":
    APP.run(transport="streamable-http", show_banner=False)