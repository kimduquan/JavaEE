package epf.gateway.transaction;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.TRANSACTION)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Transaction {

}
