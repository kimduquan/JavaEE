package epf.event.client;

import java.util.Map;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Link.Builder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * 
 */
@Path(Naming.EVENT)
public interface Event {

	/**
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response consumes(final Map<String, Object> event) throws Exception;
	
	/**
	 * @return
	 */
	static Link consumesLink() {
		Builder builder = Link.fromPath(Naming.EVENT).rel(Naming.EVENT).type(HttpMethod.POST);
		return builder.build();
	}
	
	/**
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response produces(final Map<String, Object> event) throws Exception;
	
	/**
	 * @return
	 */
	static Link producesLink() {
		Builder builder = Link.fromPath(Naming.EVENT).rel(Naming.EVENT).type(HttpMethod.PUT);
		return builder.build();
	}
}
