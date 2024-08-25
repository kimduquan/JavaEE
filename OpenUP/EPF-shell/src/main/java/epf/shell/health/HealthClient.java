package epf.shell.health;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.HEALTH)
public interface HealthClient {

	/**
	 * @return
	 */
	@GET
	@Path("ready")
	@Produces(MediaType.APPLICATION_JSON)
	Response ready();
}
