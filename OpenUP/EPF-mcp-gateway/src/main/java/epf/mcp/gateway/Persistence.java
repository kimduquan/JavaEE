package epf.mcp.gateway;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.persistence.schema.EntityType;
import io.quarkiverse.mcp.server.Prompt;
import io.quarkiverse.mcp.server.PromptArg;
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
	
	@Prompt(name = Naming.PERSISTENCE)
	@RunOnVirtualThread
	String getPersistencePrompt(@PromptArg(name = Naming.SCHEMA) final String schema) throws Exception {
		final StringBuilder prompt = new StringBuilder();
		final SchemaBuilder schemaBuilder = new SchemaBuilder();
		final String authorization = "Bearer " + jwt.getRawToken();
		final List<EntityType> entities = schemaClient.getEntities(authorization).stream().filter(entity -> entity.getTable().getSchema().equals(schema)).collect(Collectors.toList());
		schemaBuilder.entities(entities);
		prompt.append("""
		Given below Java Persistence API entity classes :
				""");
		prompt.append(schemaBuilder.build());
		return prompt.toString();
	}
	
	@Tool(name = "persistence.persist", description = "Persist an entity", structuredContent = true)
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
	
	@Tool(name = "persistence.merge", description = "Merge an entity", structuredContent = true)
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
	
	@Tool(name = "persistence.remove", description = "Remove an entity", structuredContent = true)
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
