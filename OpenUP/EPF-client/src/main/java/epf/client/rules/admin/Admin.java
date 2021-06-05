/**
 * 
 */
package epf.client.rules.admin;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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
	@Consumes(MediaType.APPLICATION_JSON)
	Response registerRuleExecutionSet(
			@PathParam("ruleSet")
			final String ruleSet,
			final InputStream input
			) throws Exception;
}
