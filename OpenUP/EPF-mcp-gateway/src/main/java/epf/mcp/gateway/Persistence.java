package epf.mcp.gateway;

import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.persistence.schema.EntityType;
import io.quarkiverse.mcp.server.Prompt;
import io.quarkiverse.mcp.server.PromptArg;
import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
@Authenticated
public class Persistence {

	@RestClient
	transient SchemaClient schemaClient;
	
	@Inject
	JsonWebToken jwt;
	
	@Prompt(name = Naming.PERSISTENCE)
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
}
