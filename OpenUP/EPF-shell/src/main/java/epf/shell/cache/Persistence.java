/**
 * 
 */
package epf.shell.cache;

import java.io.PrintWriter;
import java.util.Optional;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSource;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.SYSTEM;
import epf.shell.client.ClientUtil;
import epf.shell.security.CallerPrincipal;
import epf.shell.security.Credential;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;
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
	transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.OUT)
	transient Instance<PrintWriter> out;
	
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
		try(Client client = clientUtil.newClient(Naming.CACHE)){
			client.authorization(credential.getToken());
			try(Response response = epf.client.cache.Cache.getEntity(client, schema, entity, entityId)){
				return response.readEntity(String.class);
			}
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
		try(Client client = clientUtil.newClient(Naming.CACHE)){
			client.authorization(credential.getToken());
			try(SseEventSource stream = epf.client.cache.Cache.forEachEntity(client, schema, entity)){
				stream.register(e -> {
					out.get().println(e.readData());
				});
				stream.open();
			}
		}
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
		try(Client client = clientUtil.newClient(Naming.CACHE)){
			client.authorization(credential.getToken());
			try(Response response = epf.client.cache.Cache.getEntities(
					client, 
					schema,
					entity,
					firstResult.isEmpty() ? null : firstResult.get(), 
					maxResults.isEmpty() ? null : maxResults.get())){
				return response.readEntity(String.class);
			}
		}
	}
}
