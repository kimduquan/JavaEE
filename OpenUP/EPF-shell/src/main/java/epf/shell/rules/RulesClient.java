package epf.shell.rules;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
