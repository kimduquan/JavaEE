/**
 * 
 */
package openup.roles;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.schema.roles.RoleSet;
import openup.client.OpenUPException;
import openup.schema.roles.Role;

/**
 * @author PC
 *
 */
@Path("roles")
@RolesAllowed(openup.client.roles.Roles.ANY_ROLE)
@RequestScoped
public class Roles implements openup.client.roles.Roles {

	@Override
	public List<RoleSet> getRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Role> getRoles(final String roleSet, final String role) throws OpenUPException {
		// TODO Auto-generated method stub
		return null;
	}
}
