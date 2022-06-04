package epf.cache;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Path;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.CACHE)
public class Cache implements epf.client.cache.Cache {
	
}
