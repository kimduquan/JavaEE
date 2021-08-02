/**
 * 
 */
package epf.planning;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.client.security.Security;

/**
 * @author PC
 *
 */
@Path("planning")
@RequestScoped
@RolesAllowed(Security.DEFAULT_ROLE)
public class Planning implements epf.client.planning.Planning {

}
