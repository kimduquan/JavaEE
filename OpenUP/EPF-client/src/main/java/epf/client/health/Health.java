/**
 * 
 */
package epf.client.health;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@Path("health")
public interface Health {
	
	/**
	 * 
	 */
	String HEALTH_URL = "epf.health.url";

	/**
	 * @return
	 */
	@GET
	@Path("ready")
	@Produces(MediaType.APPLICATION_JSON)
	Response ready();
	
	/**
	 * @param client
	 * @return
	 */
	static Response ready(final Client client) {
		return client.request(
				target -> target.path("ready"), 
				req -> req.accept(MediaType.APPLICATION_JSON)
				)
				.get();
	}
}
