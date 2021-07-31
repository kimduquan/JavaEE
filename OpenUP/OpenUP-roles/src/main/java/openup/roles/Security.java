/**
 * 
 */
package openup.roles;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import openup.client.OpenUPException;

/**
 * @author PC
 *
 */
@Path("security")
@RolesAllowed(openup.client.roles.Roles.ANY_ROLE)
@RequestScoped
public class Security implements openup.client.security.Security {

	@Override
	public String authorize() throws OpenUPException {
		// TODO Auto-generated method stub
		return null;
	}
}
