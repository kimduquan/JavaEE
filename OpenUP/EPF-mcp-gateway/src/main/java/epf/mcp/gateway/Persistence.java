package epf.mcp.gateway;

import java.util.Map;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import io.quarkiverse.mcp.server.McpServer;
import io.quarkiverse.mcp.server.Tool;
import io.quarkiverse.mcp.server.ToolArg;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Authenticated
public class Persistence {

	@RestClient
	transient SchemaClient schemaClient;
	
	@RestClient
	transient PersistenceClient persistenceClient;
	
	@Inject
	JsonWebToken jwt;
	
	@McpServer(Naming.PERSISTENCE)
	@Tool(name = "persist", description = "Persist an entity", structuredContent = true)
	@RunOnVirtualThread
	String persist(
			@ToolArg(name = "schema", description = "The schema of entity")
			final String schema, 
			@ToolArg(name = "entity", description = "The entity")
			final String entity,
			@ToolArg(name = "id", description = "The identify of entity")
			final String id,
			@ToolArg(name = "data", description = "The data of entity")
			final Map<String, Object> data) throws Exception {
		final String authorization = "Bearer " + jwt.getRawToken();
		persistenceClient.persist(authorization, schema, entity, data);
		return String.format(QueryClient.QUERY_URI_FORMAT, schema, entity, id);
	}
	
	@McpServer(Naming.PERSISTENCE)
	@Tool(name = "merge", description = "Merge an entity", structuredContent = true)
	@RunOnVirtualThread
	String merge(
			@ToolArg(name = "schema", description = "The schema of entity")
			final String schema, 
			@ToolArg(name = "entity", description = "The entity")
			final String entity,
			@ToolArg(name = "id", description = "The identify of entity")
			final String id,
			@ToolArg(name = "data", description = "The data of entity")
			final Map<String, Object> data) throws Exception {
		final String authorization = "Bearer " + jwt.getRawToken();
		persistenceClient.merge(authorization, schema, entity, id, data);
		return String.format(QueryClient.QUERY_URI_FORMAT, schema, entity, id);
	}
	
	@McpServer(Naming.PERSISTENCE)
	@Tool(name = "remove", description = "Remove an entity", structuredContent = true)
	@RunOnVirtualThread
	boolean remove(
			@ToolArg(name = "schema", description = "The schema of entity")
			final String schema, 
			@ToolArg(name = "entity", description = "The entity")
			final String entity,
			@ToolArg(name = "id", description = "The identify of entity")
			final String id) throws Exception {
		final String authorization = "Bearer " + jwt.getRawToken();
		persistenceClient.remove(authorization, schema, entity, id);
		return true;
	}
}
