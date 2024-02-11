package epf.event.client;

import java.util.List;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
@Path(Naming.EVENT)
public interface Event {

	/**
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response consumes(final List<epf.event.schema.Event> events) throws Exception;
	
	/**
	 * @return
	 */
	static Link consumesLink() {
		return Link.fromPath(Naming.EVENT).rel(Naming.EVENT).type(HttpMethod.POST).build();
	}
	
	/**
	 * @param body
	 * @return
	 * @throws Exception
	 */
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response produces(final List<epf.event.schema.Event> events) throws Exception;
	
	/**
	 * @return
	 */
	static Link producesLink() {
		return Link.fromPath(Naming.EVENT).rel(Naming.EVENT).type(HttpMethod.PATCH).build();
	}
	
	/**
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	Response remove(
			@QueryParam("ids")
			final List<String> ids) throws Exception;
	
	/**
	 * @param ids
	 * @return
	 */
	static Link removeLink(final List<String> ids) {
		return Link.fromPath(Naming.EVENT).rel(Naming.EVENT).type(HttpMethod.DELETE).param("ids", String.valueOf(ids)).build();
	}
}
