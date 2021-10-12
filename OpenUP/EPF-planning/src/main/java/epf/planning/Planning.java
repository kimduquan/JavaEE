/**
 * 
 */
package epf.planning;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.PLANNING)
@RequestScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Planning implements epf.client.planning.Planning {

}
