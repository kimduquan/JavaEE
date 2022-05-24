package epf.shell.cache;

import java.io.PrintWriter;
import java.util.Optional;
import javax.ws.rs.core.Response;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.SYSTEM;
import epf.shell.client.RestClientUtil;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = Naming.CACHE)
@RequestScoped
@Function
public class Cache {

	/**
	 * 
	 */
	@Inject
	transient RestClientUtil restClient;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.OUT)
	transient PrintWriter out;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.ERR)
	transient PrintWriter err;
	
	/**
	 * @param credential
	 * @param schema
	 * @param entity
	 * @param entityId
	 */
	@Command(name = "get")
	public String get(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-s", "--schema"}, description = "Schema")
			final String schema,
			@Option(names = {"-e", "--entity"}, description = "Entity")
			final String entity, 
			@Option(names = {"-i", "--id"}, description = "ID")
			final String entityId
			) throws Exception {
		try(Response response = restClient.newClient(CacheClient.class).getEntity(credential.getAuthHeader(), schema, entity, entityId)){
			return response.readEntity(String.class);
		}
	}
	
	/**
	 * @param credential
	 * @param schema
	 * @param entity
	 * @throws Exception
	 */
	@Command(name = "for-each")
	public void forEach(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-s", "--schema"}, description = "Schema")
			final String schema,
			@Option(names = {"-e", "--entity"}, description = "Entity")
			final String entity) throws Exception {
		restClient.newClient(CacheClient.class).forEachEntity(credential.getAuthHeader(), schema, entity).subscribe(new CacheSubscriber(out, err));
	}
	
	/**
	 * @param credential
	 * @param schema
	 * @param entity
	 * @param firstResult
	 * @param maxResults
	 */
	@Command(name = "get-all")
	public String getAll(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential,
			@Option(names = {"-s", "--schema"}, description = "Schema")
			final String schema,
			@Option(names = {"-e", "--entity"}, description = "Entity")
			final String entity,
			@Option(names = {"-f", "--first"}, description = "First Result")
			final Optional<Integer> firstResult,
			@Option(names = {"-m", "--max"}, description = "Max Result(s)")
			final Optional<Integer> maxResults) throws Exception {
		try(Response response = restClient.newClient(CacheClient.class).getEntities(
				credential.getAuthHeader(), 
				schema,
				entity,
				firstResult.isEmpty() ? null : firstResult.get(), 
				maxResults.isEmpty() ? null : maxResults.get())){
			return response.readEntity(String.class);
		}
	}
}
