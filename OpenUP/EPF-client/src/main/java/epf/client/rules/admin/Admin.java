/**
 * 
 */
package epf.client.rules.admin;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import epf.util.client.Client;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.client.Entity;

/**
 * @author PC
 *
 */
@Path("rules/admin")
public interface Admin {

	/**
	 * @param ruleSet
	 * @param input
	 * @return
	 * @throws Exception
	 */
	@Path("{ruleSet}")
	@PUT
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	Response registerRuleExecutionSet(
			@PathParam("ruleSet")
			final String name,
			@Context
			final UriInfo uriInfo,
			final InputStream input
			) throws Exception;
	
	/**
	 * @param client
	 * @param name
	 * @param input
	 * @return
	 */
	static Response registerRuleExecutionSet(final Client client, final String name, final InputStream input) {
		return client
				.request(
						target -> target.path("admin").path(name), 
						req -> req
						)
				.put(
						Entity.entity(
								input, 
								MediaType.APPLICATION_OCTET_STREAM
								)
						);
	}
	
	/**
	 * @param name
	 * @param uriInfo
	 * @return
	 * @throws Exception
	 */
	@Path("{ruleSet}")
	@DELETE
	Response deregisterRuleExecutionSet(
			@PathParam("ruleSet")
			final String name,
			@Context
			final UriInfo uriInfo
			) throws Exception;
	
	/**
	 * @param client
	 * @param name
	 * @return
	 * @throws Exception
	 */
	static Response deregisterRuleExecutionSet(
			final Client client,
			final String name
			) throws Exception{
		return client
				.request(
						target -> target.path("admin").path(name), 
						req -> req
						)
				.delete();
	}
}
