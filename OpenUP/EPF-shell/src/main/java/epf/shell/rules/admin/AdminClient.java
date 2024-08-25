package epf.shell.rules.admin;

import java.io.InputStream;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
