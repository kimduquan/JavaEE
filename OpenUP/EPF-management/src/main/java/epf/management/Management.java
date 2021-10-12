package epf.management;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;

import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.MANAGEMENT)
@ApplicationScoped
public class Management implements epf.client.management.Management {
	
}
