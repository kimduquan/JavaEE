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
import epf.util.Var;
import epf.util.client.Client;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

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
	 * @param token
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	@Command(name = "entities")
	public List<Entity> getEntities(
			@Option(names = {"-t", "--token"}, description = "Token") 
			final String token,
			@Option(names = {"-u", "--unit"}, description = "Unit")
			final String unit) throws Exception{
		try(Client client = clientUtil.newClient(schemaUrl.get())){
			client.authorization(token);
			return epf.client.schema.Schema.getEntities(client, unit).readEntity(new GenericType<List<Entity>>() {});
		}
	}
}
