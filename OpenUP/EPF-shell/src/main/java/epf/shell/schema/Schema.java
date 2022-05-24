package epf.shell.schema;

import epf.naming.Naming;
import epf.shell.Function;
import epf.shell.client.RestClientUtil;
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
	transient RestClientUtil restClient;
	
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
		try(Response response = restClient.newClient(SchemaClient.class).getEntities(credential.getAuthHeader())){
			return response.readEntity(String.class);
		}
	}
}
