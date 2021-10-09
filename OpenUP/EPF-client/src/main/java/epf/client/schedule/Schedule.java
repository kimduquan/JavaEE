/**
 * 
 */
package epf.client.schedule;

import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import epf.client.util.Client;

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
	 * 
	 */
	String PATH = "path";
	
	/**
	 * 
	 */
	String SHELL = "shell";

	/**
	 * @param path
	 * @param delay
	 * @param unit
	 * @return
	 */
	@Path("{path}")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	long schedule(
			@PathParam(PATH)
			@NotBlank
			final String path,
			@FormParam("delay") 
			final long delay, 
			@FormParam("unit") 
			final TimeUnit unit);
	
	/**
	 * @param client
	 * @param path
	 * @param delay
	 * @param unit
	 * @return
	 */
	static Response schedule(
			final Client client, 
			final String path,
			final long delay, 
			final TimeUnit unit) {
		final Form form = new Form()
				.param("delay", String.valueOf(delay))
				.param("unit", unit.name());
		return client.request(
				target -> target.path(path), 
				req -> req.accept(MediaType.APPLICATION_JSON)
				)
				.post(Entity.form(form));
	}
	
	/**
	 * @param path
	 * @param id
	 */
	@Path("{path}")
	@DELETE
	void cancel(
			@PathParam(PATH)
			@NotBlank
			final String path,
			@QueryParam("id") 
			@NotNull 
			final long id);
	
	/**
	 * @param client
	 * @param id
	 * @return
	 */
	static Response cancel(
			final Client client,
			final String path, 
			final long id) {
		return client.request(
				target -> target.path(path).queryParam("id", id), 
				req -> req
				)
				.delete();
	}
	
	/**
	 * @param path
	 * @param initialDelay
	 * @param period
	 * @param unit
	 * @return
	 */
	@Path("{path}/rate")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	long scheduleAtFixedRate(
			@PathParam(PATH)
			@NotBlank
			final String path,
			@FormParam("init") 
			final long initialDelay, 
			@FormParam("period") 
			final long period, 
			@FormParam("unit") 
			final TimeUnit unit);
	
	/**
	 * @param client
	 * @param path
	 * @param initialDelay
	 * @param period
	 * @param unit
	 * @return
	 */
	static Response scheduleAtFixedRate(
			final Client client,
			final String path,
			final long initialDelay,
			final long period,
			final TimeUnit unit) {
		final Form form = new Form()
				.param("init", String.valueOf(initialDelay))
				.param("period", String.valueOf(period))
				.param("unit", unit.name());
		return client.request(
				target -> target.path(path), 
				req -> req.accept(MediaType.APPLICATION_JSON)
				)
				.post(Entity.form(form));
	}
	
	/**
	 * @param path
	 * @param initialDelay
	 * @param delay
	 * @param unit
	 * @return
	 */
	@Path("{path}/delay")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	long scheduleWithFixedDelay(
			@PathParam(PATH)
			@NotBlank
			final String path,
			@FormParam("init")
			final long initialDelay,
			@FormParam("delay")
			final long delay,
			@FormParam("unit")
			final TimeUnit unit);
	
	/**
	 * @param client
	 * @param path
	 * @param initialDelay
	 * @param delay
	 * @param unit
	 * @return
	 */
	static Response scheduleWithFixedDelay(
			final Client client,
			final String path,
			final long initialDelay,
			final long delay,
			final TimeUnit unit) {
		final Form form = new Form()
				.param("init", String.valueOf(initialDelay))
				.param("delay", String.valueOf(delay))
				.param("unit", unit.name());
		return client.request(
				target -> target.path(path), 
				req -> req.accept(MediaType.APPLICATION_JSON)
				)
				.post(Entity.form(form));
	}
}
