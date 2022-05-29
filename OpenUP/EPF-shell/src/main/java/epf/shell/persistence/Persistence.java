package epf.shell.persistence;

import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = Naming.PERSISTENCE)
@RequestScoped
@Function
public class Persistence {
	
	/**
	 * 
	 */
	@Inject
	@RestClient
	transient PersistenceClient persistence;
	
	/**
	 * @param credential
	 * @param schema
	 * @param entity
	 * @param data
	 */
	@Command(name = "persist")
	public String persist(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-s", "--schema"}, description = "Schema")
			final String schema,
			@Option(names = {"-e", "--entity"}, description = "Entity")
			final String entity, 
			@Option(names = {"-d", "--data"}, description = "Entity", interactive = true, echo = true)
			final String data) throws Exception {
		try(Response response = persistence.persist(credential.getAuthHeader(), schema, entity, data)){
			return response.readEntity(String.class);
		}
	}
	
	/**
	 * @param credential
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @param data
	 */
	@Command(name = "merge")
	public void merge(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-s", "--schema"}, description = "Schema")
			final String schema,
			@Option(names = {"-e", "--entity"}, description = "Entity")
			final String entity, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId,
			@Option(names = {"-d", "--data"}, description = "Entity", interactive = true, echo = true)
			final String data) throws Exception {
		persistence.merge(credential.getAuthHeader(), schema, entity, entityId, data);
	}
	
	/**
	 * @param credential
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @throws Exception
	 */
	@Command(name = "remove")
	public void remove(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-s", "--schema"}, description = "Schema")
			final String schema,
			@Option(names = {"-e", "--entity"}, description = "Entity")
			final String entity, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId) throws Exception {
		persistence.remove(credential.getAuthHeader(), schema, entity, entityId);
	}
}
