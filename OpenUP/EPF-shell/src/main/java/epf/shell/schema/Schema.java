/**
 * 
 */
package epf.shell.schema;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.security.Credential;
import epf.shell.security.CallerPrincipal;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
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
	@RestClient
	transient SchemaClient schema;
	
	/**
	 * @param credential
	 * @return
	 * @throws Exception
	 */
	@Command(name = "entities")
	public String getEntities(
			@ArgGroup(exclusive = true, multiplicity = "1")
			@CallerPrincipal
			final Credential credential) throws Exception{
		try(Response response = schema.getEntities(credential.getAuthHeader())){
			return response.readEntity(String.class);
		}
	}
}
