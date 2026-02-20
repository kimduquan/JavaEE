package epf.mcp.gateway;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.persistence.schema.EntityType;
import io.quarkiverse.mcp.server.Prompt;
import io.quarkiverse.mcp.server.PromptArg;
import io.quarkiverse.mcp.server.PromptMessage;
import io.quarkus.security.Authenticated;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Authenticated
public class Gateway {
	
	@Inject
	JsonWebToken jwt;

	@RestClient
	transient SchemaClient schemaClient;
	
	@Prompt(name = Naming.GATEWAY)
	@RunOnVirtualThread
	PromptMessage getGatewayPrompt() throws Exception {
		final StringBuilder prompt = new StringBuilder();
		final String authorization = "Bearer " + jwt.getRawToken();
		final Set<String> schemas = schemaClient.getEntities(authorization).stream().map(entityType -> entityType.getTable().getSchema()).collect(Collectors.toSet());
		prompt.append("""
		Given below schemas : \n
		""");
		schemas.forEach(schema -> {
			prompt.append(String.format("\t - '%s'\n", schema));
		});
		return PromptMessage.withUserRole(prompt.toString());
	}
	
	@Prompt(name = Naming.PERSISTENCE)
	@RunOnVirtualThread
	PromptMessage getPersistencePrompt(@PromptArg(name = "schema_name") final String schemaName) throws Exception {
		final StringBuilder prompt = new StringBuilder();
		final SchemaBuilder schemaBuilder = new SchemaBuilder();
		final String authorization = "Bearer " + jwt.getRawToken();
		final List<EntityType> entities = schemaClient.getEntities(authorization).stream().filter(entity -> entity.getTable().getSchema().equals(schemaName)).collect(Collectors.toList());
		schemaBuilder.entities(entities);
		prompt.append("""
		Given below Jakarta Persistence API entity classes :
				""");
		prompt.append(schemaBuilder.build());
		return PromptMessage.withUserRole(prompt.toString());
	}

	@Prompt(name = Naming.QUERY)
	@RunOnVirtualThread
	PromptMessage getQueryPrompt(@PromptArg(name = "schema_name") final String schemaName) throws Exception {
		final StringBuilder prompt = new StringBuilder();
		final SchemaBuilder schemaBuilder = new SchemaBuilder();
		final String authorization = "Bearer " + jwt.getRawToken();
		final List<EntityType> entities = schemaClient.getEntities(authorization).stream().filter(entity -> entity.getTable().getSchema().equals(schemaName)).collect(Collectors.toList());
		schemaBuilder.entities(entities);
		prompt.append("""
		Given below Jakarta Persistence API entity classes :
				""");
		prompt.append(schemaBuilder.build());
		return PromptMessage.withUserRole(prompt.toString());
	}
}
