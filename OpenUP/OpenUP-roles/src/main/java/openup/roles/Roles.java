/**
 * 
 */
package openup.roles;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import openup.schema.roles.Role;

/**
 * @author PC
 *
 */
@Path("roles")
@RolesAllowed(epf.schema.roles.Role.DEFAULT_ROLE)
@RequestScoped
public class Roles implements openup.client.roles.Roles {

	@Override
	public List<Role> getRoles() {
		return null;
	}
}
