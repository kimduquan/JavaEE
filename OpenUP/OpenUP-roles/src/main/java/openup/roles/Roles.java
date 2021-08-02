/**
 * 
 */
package openup.roles;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.client.security.Security;

/**
 * @author PC
 *
 */
@Path("roles")
@RolesAllowed(Security.DEFAULT_ROLE)
@RequestScoped
public class Roles implements openup.client.roles.Roles {
	
}
