package epf.shell.query;

import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.naming.Naming.Query.Client;
import epf.shell.Function;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * 
 */
@Command(name = Naming.QUERY)
@RequestScoped
@Function
public class Query {

	/**
	 * 
	 */
	@RestClient
	transient QueryClient query;
	
	/**
	 * @param credential
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 */
	@Command(name = "get")
	public String getEntity(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-s", "--schema"}, description = "Schema")
			final String schema,
			@Option(names = {"-e", "--entity"}, description = "Entity")
			final String entity, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId) {
		try(Response response = query.getEntity(credential.getAuthHeader(), schema, entity, entityId)){
			return response.readEntity(String.class);
		}
	}
	
	/**
	 * @param credential
	 * @param schema
	 * @param entity
	 * @return
	 */
	@Command(name = "count")
	public int countEntity(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-s", "--schema"}, description = "Schema")
			final String schema,
			@Option(names = {"-e", "--entity"}, description = "Entity")
			final String entity) {
		try(Response response = query.countEntity(credential.getAuthHeader(), schema, entity)){
			return Integer.parseInt(response.getHeaderString(Client.ENTITY_COUNT));
		}
	}
	
	/**
	 * @param credential
	 * @param entityIds
	 * @return
	 */
	@Command(name = "fetch")
	public String fetchEntities(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-ids", "--entityIds"}, description = "Entity IDs", interactive = true, echo = true)
			final String entityIds) {
		try(Response response = query.fetchEntities(credential.getAuthHeader(), entityIds)){
			return response.readEntity(String.class);
		}
	}
}
