/**
 * 
 */
package epf.planning;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.PLANNING)
@RequestScoped
public class Planning implements epf.client.planning.Planning {

}
