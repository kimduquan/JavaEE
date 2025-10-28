package epf.mcp.gateway;

import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Gateway {

	@Tool(description = "Execute a JPQL query", structuredContent = true)  
    Object executeQuery(@ToolArg(description = "The JPQL query") final String query) {
        return null;
    }
}
