package epf.gateway.event;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.EVENT)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Event {

}
