/**
 * 
 */
package openup.client.security;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import openup.client.OpenUPException;

/**
 * @author PC
 *
 */
@Path("security")
public interface Security {
	
	/**
	 * @return
	 * @throws OpenUPException
	 */
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	String authorize() throws OpenUPException;
}
