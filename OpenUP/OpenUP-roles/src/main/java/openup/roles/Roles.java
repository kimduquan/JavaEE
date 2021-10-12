/**
 * 
 */
package openup.roles;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path("roles")
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@RequestScoped
public class Roles implements openup.client.roles.Roles {
	
}
