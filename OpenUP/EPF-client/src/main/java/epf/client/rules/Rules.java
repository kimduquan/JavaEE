package epf.client.rules;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.client.util.Client;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.RULES)
public interface Rules {

	/**
	 * 
	 */
	String PROVIDER_CLASS = "epf.rules.provider.class";
	
	/**
	 * 
	 */
	String PROVIDER_URI = "epf.rules.provider.uri";
	
	/**
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
			@PathParam("ruleSet")
			final String ruleSet,
			final InputStream input
			) throws Exception;
	
	/**
	 * @param client
	 * @param ruleSet
	 * @param input
	 * @return
	 * @throws Exception
	 */
	static Response executeRules(
			final Client client, 
			final String ruleSet,
			final String input
			) throws Exception{
		return client
				.request(
						target -> target.path(ruleSet), 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.post(Entity.entity(input, MediaType.APPLICATION_JSON));
	}
	
	/**
	 * @param ruleSet
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Path("{ruleSet}")
	@Produces(MediaType.APPLICATION_JSON)
	Response executeRules(
			@PathParam("ruleSet")
			final String ruleSet
			) throws Exception;
	
	/**
	 * @param client
	 * @param ruleSet
	 * @return
	 * @throws Exception
	 */
	static Response executeRules(
			final Client client, 
			final String ruleSet) throws Exception{
		return client
				.request(
						target -> target.path(ruleSet), 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.put(Entity.json(null));
	}
	
	/**
	 * @param ruleSet
	 * @param input
	 * @throws Exception
	 */
	@PATCH
	@Path("{ruleSet}")
	@Consumes(MediaType.APPLICATION_JSON)
	Response addObject(
			@PathParam("ruleSet")
			final String ruleSet,
			final InputStream input) throws Exception;
	
	/**
	 * @param client
	 * @param ruleSet
	 * @param input
	 * @return
	 */
	static Response addObject(
			final Client client, 
			final String ruleSet, 
			final String input) {
		return client
				.request(
						target -> target.path(ruleSet), 
						req -> req
						)
				.method(HttpMethod.PATCH, Entity.entity(input, MediaType.APPLICATION_JSON));
	}
	
	/**
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response getRegistrations() throws Exception;
	
	/**
	 * @param client
	 * @return
	 */
	static Response getRegistrations(final Client client) {
		return client
				.request(
						target -> target, 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.get();
	}
}
