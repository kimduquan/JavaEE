package epf.management;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;

/**
 * @author PC
 *
 */
@Path("management")
@ApplicationScoped
public class Management implements epf.client.management.Management {
	
}
