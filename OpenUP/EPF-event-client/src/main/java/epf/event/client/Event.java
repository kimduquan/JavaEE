package epf.event.client;

import java.net.URI;
import java.util.List;
import java.util.Map;
import epf.naming.Naming;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.Link.Builder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

/**
 * 
 */
@Path(Naming.EVENT)
public interface Event {

	/**
	 * @param uriInfo
	 * @param event
	 * @return
	 * @throws Exception
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response consumes(
			@Context
			final UriInfo uriInfo,
			final Map<String, Object> event) throws Exception;
	
	/**
	 * @param eventLink
	 * @return
	 */
	static Link consumesLink(final Link eventLink) {
		UriBuilder uri = UriBuilder.fromPath(Naming.EVENT);
		final Map<String, String> params = eventLink.getParams();
		for(Map.Entry<String, String> entry : params.entrySet()) {
			uri = uri.queryParam(entry.getKey(), entry.getValue());
		}
		Builder builder = Link.fromUriBuilder(uri).rel(Naming.EVENT).type(HttpMethod.POST);
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
	 * @param uri
	 * @param params
	 * @return
	 */
	static Builder link(final URI uri, final Map<String, Object> params) {
		Builder builder = Link.fromUri(uri);
		for(Map.Entry<String, Object> entry : params.entrySet()) {
			builder = builder.param(entry.getKey(), entry.getValue().toString());
		}
		if(params.containsKey("rel")) {
			builder = builder.rel(params.get("rel").toString());
		}
		if(params.containsKey("title")) {
			builder = builder.title(params.get("title").toString());
		}
		if(params.containsKey("type")) {
			builder = builder.type(params.get("type").toString());
		}
		if(params.containsKey("uri")) {
			builder = builder.uri(params.get("uri").toString());
		}
		return builder;
	}
	
	/**
	 * @param events
	 * @return
	 * @throws Exception
	 */
	@PATCH
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response observes(final List<Map<String, Object>> events) throws Exception;
	
	/**
	 * @return
	 */
	static Link observesLink() {
		Builder builder = Link.fromPath(Naming.EVENT).rel(Naming.EVENT).type(HttpMethod.PATCH);
		return builder.build();
	}
}
