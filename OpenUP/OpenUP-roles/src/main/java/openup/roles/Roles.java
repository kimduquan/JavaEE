/**
 * 
 */
package openup.roles;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;

/**
 * @author PC
 *
 */
@Path("roles")
@RolesAllowed(epf.schema.roles.Role.DEFAULT_ROLE)
@RequestScoped
public class Roles implements openup.client.roles.Roles {
	
}
