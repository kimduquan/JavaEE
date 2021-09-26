/**
 * 
 */
package epf.net;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import epf.client.security.Security;

/**
 * @author PC
 *
 */
@Path("net")
@ApplicationScoped
@RolesAllowed(Security.DEFAULT_ROLE)
public class Net implements epf.client.net.Net {

	@Override
	public String rewriteUrl(final String url) {
		// TODO Auto-generated method stub
		return null;
	}

}
