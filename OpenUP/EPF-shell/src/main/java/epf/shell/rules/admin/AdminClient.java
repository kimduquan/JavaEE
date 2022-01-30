package epf.shell.rules.admin;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.Rules.RULES_ADMIN)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface AdminClient {

	/**
	 * @param token
	 * @param name
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@Path("{ruleSet}")
	@PUT
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	Response registerRuleExecutionSet(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@PathParam("ruleSet")
			final String name,
			final InputStream input
			) throws Exception;
	
	/**
	 * @param token
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@Path("{ruleSet}")
	@DELETE
	Response deregisterRuleExecutionSet(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@PathParam("ruleSet")
			final String name
			) throws Exception;
}
