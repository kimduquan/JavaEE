package epf.shell.rules;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.RULES)
@RegisterRestClient(configKey = Naming.Client.CLIENT_CONFIG)
public interface RulesClient {

	/**
	 * @param token
	 * @param ruleSet
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@POST
	@Path("{ruleSet}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response executeRules(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@PathParam("ruleSet")
			final String ruleSet,
			final String input
			) throws Exception;
	
	/**
	 * @param token
	 * @param ruleSet
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("{ruleSet}")
	@Produces(MediaType.APPLICATION_JSON)
	Response executeRules(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@PathParam("ruleSet")
			final String ruleSet
			) throws Exception;
	
	/**
	 * @param token
	 * @param ruleSet
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@PATCH
	@Path("{ruleSet}")
	@Consumes(MediaType.APPLICATION_JSON)
	Response addObject(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token,
			@PathParam("ruleSet")
			final String ruleSet,
			final String input) throws Exception;
	
	/**
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response getRegistrations(
			@HeaderParam(HttpHeaders.AUTHORIZATION)
    		final String token
			) throws Exception;
}
