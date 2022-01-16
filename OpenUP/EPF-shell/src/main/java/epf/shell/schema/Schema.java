/**
 * 
 */
package epf.shell.schema;

import java.util.List;
import javax.ws.rs.core.GenericType;
import epf.client.gateway.GatewayUtil;
import epf.client.schema.Entity;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.client.ClientUtil;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;

/**
 * @author PC
 *
 */
@Command(name = Naming.SCHEMA)
@RequestScoped
@Function
public class Schema {
	
	/**
	 * 
	 */
	@Inject
	transient ClientUtil clientUtil;
	
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
		try(Client client = clientUtil.newClient(GatewayUtil.get(Naming.SCHEMA))){
			client.authorization(credential.getToken());
			return epf.client.schema.Schema.getEntities(client).readEntity(new GenericType<List<Entity>>() {});
		}
	}
}
