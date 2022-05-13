package epf.transaction;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import epf.naming.Naming;

/**
 * 
 */
@Path(Naming.TRANSACTION)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
@ApplicationScoped
public class Transaction implements epf.client.transaction.Transaction {

}
