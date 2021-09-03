/**
 * 
 */
package epf.gateway.cache;

import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * @author PC
 *
 */
@Path("cache")
public interface CacheUtil {

	/**
	 * @param tid
	 * @return
	 */
	@GET
	@Path("security")
	@Produces(MediaType.APPLICATION_JSON)
	Map<String, Object> getToken(@QueryParam("tid") final String tid);
}
