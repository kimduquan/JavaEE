/**
 * 
 */
package epf.client.schedule;

import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@Path("schedule")
public interface Schedule {
	
	/**
	 * 
	 */
	String SCHEDULE_URL = "epf.schedule.url";

	/**
	 * @param delay
	 * @param unit
	 * @return
	 */
	@Path("shell")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	long scheduleShell(@FormParam("delay") final long delay, @FormParam("unit") final TimeUnit unit);
	
	/**
	 * @param client
	 * @param delay
	 * @param unit
	 * @return
	 */
	static Response scheduleShell(final Client client, final long delay, final TimeUnit unit) {
		final Form form = new Form().param("delay", String.valueOf(delay)).param("unit", unit.name());
		return client.request(
				target -> target.path("shell"), 
				req -> req.accept(MediaType.APPLICATION_JSON)
				)
				.post(Entity.form(form));
	}
	
	/**
	 * @param id
	 */
	@Path("shell")
	@DELETE
	void cancelShell(@QueryParam("id") @NotNull final long id);
	
	/**
	 * @param client
	 * @param id
	 * @return
	 */
	static Response cancelShell(final Client client, final long id) {
		return client.request(
				target -> target.path("shell").queryParam("id", id), 
				req -> req
				)
				.delete();
	}
}
