/**
 * 
 */
package epf.client.cache;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import javax.ws.rs.sse.SseEventSource;
import epf.client.security.Token;
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
	 * @param entity
	 * @param entityId
	 * @return
	 */
	@GET
    @Path("persistence/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntity(
    		@PathParam("schema")
            @NotNull
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotNull
            @NotBlank
            final String entity,
            @PathParam("id")
            @NotNull
            @NotBlank
            final String entityId
            );
	
	/**
	 * @param client
	 * @param cls
	 * @param schema
	 * @param entity
	 * @param entityId
	 */
	static <T> T getEntity(final Client client, final Class<T> cls, final String schema, final String entity, final String entityId) {
		return client.request(
    			target -> target.path("persistence").path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(cls);
	}
	
	/**
	 * @param client
	 * @param schema
	 * @param entity
	 * @param entityId
	 * @return
	 */
	static Response getEntity(final Client client, final String schema, final String entity, final String entityId) {
		return client.request(
    			target -> target.path("persistence").path(schema).path(entity).path(entityId), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
				.get();
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	@GET
    @Path("persistence/{schema}/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntities(
    		@PathParam("schema")
            @NotNull
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotNull
            @NotBlank
            final String entity,
            @QueryParam("firstResult")
            final Integer firstResult,
            @QueryParam("maxResults")
    		final Integer maxResults
            );
	
	/**
	 * @param client
	 * @param cls
	 * @param schema
	 * @param entity
	 * @param firstResult
	 * @param maxResults
	 */
	static <T> List<T> getEntities(
			final Client client, 
			final Class<T> cls, 
			final String schema,
			final String entity, 
			final Integer firstResult, 
			final Integer maxResults) {
		return client.request(
    			target -> target.path("persistence").path(schema).path(entity).queryParam("firstResult", firstResult).queryParam("maxResults", maxResults), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
    			.get(new GenericType<List<T>>() {});
	}
	
	/**
	 * @param client
	 * @param schema
	 * @param entity
	 * @param firstResult
	 * @param maxResults
	 */
	static Response getEntities(
			final Client client, 
			final String schema,
			final String entity, 
			final Integer firstResult, 
			final Integer maxResults) {
		return client.request(
    			target -> target.path("persistence").path(schema).path(entity).queryParam("firstResult", firstResult).queryParam("maxResults", maxResults), 
    			req -> req.accept(MediaType.APPLICATION_JSON)
    			)
				.get();
	}
	
	/**
	 * @param schema
	 * @param entity
	 * @param sseEventSink
	 * @param sse
	 */
	@GET
	@Path("persistence/{schema}")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	void forEachEntity(
			@PathParam("schema")
            @NotNull
            @NotBlank
            final String schema,
			@QueryParam("entity")
			@NotNull
			@NotBlank
			final String entity, 
			@Context 
			final SseEventSink sseEventSink, 
			@Context 
			final Sse sse);
	
	/**
	 * @param client
	 * @param schema
	 * @param entity
	 * @return
	 */
	static SseEventSource forEachEntity(final Client client, final String schema, final String entity) {
		return client.stream(target -> target.path("persistence").path(schema).queryParam("entity", entity), req -> req);
	}
	
	/**
	 * @param tokenId
	 * @return
	 */
	@GET
    @Path("security")
	@Produces(MediaType.APPLICATION_JSON)
	Token getToken(@QueryParam("tid") final String tokenId);
	
	/**
	 * @param client
	 * @param tokenId
	 * @return
	 */
	static Token getToken(final Client client, final String tokenId) {
		return client
				.request(target -> target.path("security").queryParam("tid", tokenId), 
						req -> req.accept(MediaType.APPLICATION_JSON)
						)
				.get(Token.class);
	}
}
