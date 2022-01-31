package epf.gateway.cache;

import java.util.concurrent.CompletionStage;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;

/**
 * @author PC
 *
 */
@Blocking
@Path(Naming.CACHE)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Cache {

	/**
	 * 
	 */
	@Inject
    transient Application request;
	
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
    public CompletionStage<Response> getEntity(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId) throws Exception {
        return request.request(Naming.CACHE, context, headers, uriInfo, req, null);
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
    public CompletionStage<Response> getEntities(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity) throws Exception {
        return request.request(Naming.CACHE, context, headers, uriInfo, req, null);
    }
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param sseEventSink
	 * @param sse
	 * @param schema
	 */
	@PermitAll
	@GET
	@Path("persistence/{schema}")
	@Produces(MediaType.SERVER_SENT_EVENTS)
	public void forEachEntity(
    		@Context 
    		final SecurityContext context,
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
		request.stream(Naming.CACHE, headers, uriInfo, sseEventSink, sse);
	}
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@PermitAll
	@GET
	@Path("security")
	@Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> getToken(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req) throws Exception {
        return request.request(Naming.CACHE, context, headers, uriInfo, req, null);
    }
}
