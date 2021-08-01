/**
 * 
 */
package openup.client.security;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import epf.util.client.Client;
import openup.client.OpenUPException;

/**
 * @author PC
 *
 */
@Path("roles")
public interface Security {
	
	/**
	 * @return
	 * @throws OpenUPException
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	String authorize() throws Exception;
	
	/**
	 * @param client
	 * @return
	 * @throws Exception
	 */
	static String authorize(final Client client) throws Exception{
		return client.request(
    			target -> target, 
    			req -> req.accept(MediaType.TEXT_PLAIN)
    			)
    			.post(null, String.class);
	}
}
