/**
 * 
 */
package epf.client.rules;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author PC
 *
 */
@Path("rules")
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
	 * 
	 */
	String RULES_URL = "epf.rules.url";
	
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
}
