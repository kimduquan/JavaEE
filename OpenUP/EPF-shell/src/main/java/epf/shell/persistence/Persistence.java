package epf.shell.persistence;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import jakarta.enterprise.context.RequestScoped;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = Naming.PERSISTENCE)
@RequestScoped
@Function
public class Persistence {
	
	@RestClient
	transient PersistenceClient persistence;
	
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
	
	@Command(name = "merge")
	public String merge(
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
		try(Response response = persistence.merge(credential.getAuthHeader(), schema, entity, entityId, data)){
			return response.readEntity(String.class);
		}
	}
	
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
