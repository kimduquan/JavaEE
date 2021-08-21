/**
 * 
 */
package epf.client.cache;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import javax.ws.rs.sse.SseEventSource;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
@Path("cache")
public interface Cache {

	/**
	 * 
	 */
	String CACHE_URL = "epf.cache.url";
	
	/**
	 * @param schema
	 * @param name
	 * @param entityId
	 * @return
	 */
	@GET
    @Path("persistence/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntity(
            @PathParam("entity")
            @NotBlank
            final String name,
            @PathParam("id")
            @NotBlank
            final String entityId
            );
	
	/**
	 * @param <T>
	 * @param client
	 * @param cls
	 * @param entity
	 * @param entityId
	 * @return
	 */
	static <T> T getEntity(final Client client, final Class<T> cls, final String entity, final String entityId) {
		return client.request(
    			target -> target.path("persistence").path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(cls);
	}
	
	/**
	 * @param client
	 * @param entity
	 * @param entityId
	 * @return
	 */
	static Response getEntity(final Client client, final String entity, final String entityId) {
		return client.request(
    			target -> target.path("persistence").path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
				.get();
	}
	
	/**
	 * @param entity
	 * @param sseEventSink
	 * @param sse
	 */
	@GET
	@Path("persistence")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	void forEachEntity(
			@QueryParam("entity")
			@NotBlank
			final String entity, 
			@Context 
			final SseEventSink sseEventSink, 
			@Context 
			final Sse sse);
	
	/**
	 * @param client
	 * @param entity
	 */
	static SseEventSource forEachEntity(final Client client, final String entity) {
		return client.stream(target -> target.path("persistence").queryParam("entity", entity), req -> req);
	}
}
