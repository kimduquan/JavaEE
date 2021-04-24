/**
 * 
 */
package epf.client.cache;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
	
	@GET
    @Path("persistence/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    Response getEntity(
            @PathParam("schema")
            @NotBlank
            final String schema,
            @PathParam("entity")
            @NotBlank
            final String name,
            @PathParam("id")
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
}
