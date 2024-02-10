package epf.gateway.event;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.EVENT)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Event {

}
