/**
 * 
 */
package epf.planning;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.schema.roles.Role;

/**
 * @author PC
 *
 */
@Path("planning")
@RequestScoped
@RolesAllowed(Role.DEFAULT_ROLE)
public class Planning implements epf.client.planning.Planning {

}
