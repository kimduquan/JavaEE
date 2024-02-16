package epf.event.client;

import java.net.URI;
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
	
	/**
	 * 
	 */
	String EPF_EVENT_SOURCE_URI_FORMAT = "%s://%s/%s";
	
	/**
	 * @param source
	 * @return
	 * @throws Exception
	 */
	static URI getEventSource(final Link source) throws Exception {
		final String uri = String.format(EPF_EVENT_SOURCE_URI_FORMAT, source.getType(), source.getRel(), source.getUri().toString());
		return new URI(uri);
	}
	
	/**
	 * @param source
	 * @return
	 * @throws Exception
	 */
	static Builder getEventSource(final URI source) throws Exception {
		final String type = source.getScheme();
		final String rel = source.getHost();
		final String path = source.getPath() + source.getQuery() + source.getFragment();
		return Link.fromPath(path).rel(rel).type(type);
	}
}
