/**
 * 
 */
package epf.gateway.planning;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;

/**
 * @author PC
 *
 */
@Blocking
@Path(Naming.PLANNING)
@ApplicationScoped
public class Planning {
	
}
