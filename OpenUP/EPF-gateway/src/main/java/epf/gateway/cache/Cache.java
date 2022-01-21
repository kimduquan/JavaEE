/**
 * 
 */
package epf.gateway.cache;

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
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import epf.gateway.Request;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.CACHE)
@ApplicationScoped
public class Cache {

	/**
	 * 
	 */
	@Inject
    transient Request request;
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param schema
	 * @param entity
	 * @param entityId
	 */
	@GET
	@Path("persistence/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> getEntity(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId) throws Exception {
        return request.request(headers, uriInfo, req, null);
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param schema
	 * @param entity
	 */
	@GET
	@Path("persistence/{schema}/{entity}")
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> getEntities(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity) throws Exception {
        return request.request(headers, uriInfo, req, null);
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param sseEventSink
	 * @param sse
	 * @param schema
	 */
	@GET
	@Path("persistence/{schema}")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void forEachEntity(
			@Context 
			final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
			@Context 
			final SseEventSink sseEventSink, 
			@Context 
			final Sse sse,
			@PathParam("schema") 
			final String schema) throws Exception {
		request.stream(headers, uriInfo, sseEventSink, sse);
	}
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path("security")
	@Produces(MediaType.APPLICATION_JSON)
    @Asynchronous
    public CompletionStage<Response> getToken(
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) throws Exception {
        return request.request(headers, uriInfo, req, null);
    }
}
