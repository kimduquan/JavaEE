/**
 * 
 */
package epf.gateway.cache;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;

/**
 * @author PC
 *
 */
@Path("cache")
@ApplicationScoped
public class Cache {

	/**
	 * 
	 */
	@Inject
    private transient Request request;
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param entity
	 * @param entityId
	 * @return
	 * @throws Exception 
	 */
	@GET
	@Path("persistence/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> getEntity(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId) throws Exception {
        return CompletableFuture.completedFuture(request.request(headers, uriInfo, req, null));
    }
}
