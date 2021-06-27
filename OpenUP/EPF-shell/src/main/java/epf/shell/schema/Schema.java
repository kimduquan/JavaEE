/**
 * 
 */
package epf.shell.schema;

import java.net.URI;
import java.util.List;
import javax.ws.rs.core.GenericType;
import epf.client.schema.Entity;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import epf.util.Var;
import epf.util.client.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = "schema")
@RequestScoped
@Function
public class Schema {

	/**
	 * 
	 */
	@Inject @Named(epf.client.schema.Schema.SCHEMA_URL)
	private transient Var<URI> schemaUrl;
	
	/**
	 * 
	 */
	@Inject
	private transient ClientUtil clientUtil;
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	@Command(name = "entities")
	public List<Entity> getEntities(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential) throws Exception{
		try(Client client = clientUtil.newClient(schemaUrl.get())){
			client.authorization(credential.getToken());
			return epf.client.schema.Schema.getEntities(client).readEntity(new GenericType<List<Entity>>() {});
		}
	}
}
