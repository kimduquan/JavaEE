package epf.gateway.query;

import java.util.List;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.QUERY)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Query {

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
    @Path("entity/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> getEntity(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId) throws Exception {
        return request.request(Naming.QUERY, context, headers, uriInfo, req, null);
    }
	
	/**
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param schema
	 * @param entity
	 */
	@HEAD
	@Path("entity/{schema}/{entity}")
	public CompletionStage<Response> countEntity(
			@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity
            ) throws Exception {
        return request.request(Naming.QUERY, context, headers, uriInfo, req, null);
    }
	
	/**
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param schema
	 * @param paths
	 */
	@GET
    @Path("query/{schema}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
	public CompletionStage<Response> executeQuery(
    		@Context 
    		final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final javax.ws.rs.core.Request req,
    		@PathParam("schema")
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths) throws Exception { 
		return request.request(Naming.QUERY, context, headers, uriInfo, req, null);
		}
	
	/**
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param schema
	 * @param paths
	 */
	@HEAD
    @Path("query/{schema}/{criteria: .+}")
	public CompletionStage<Response> executeCountQuery(
			@Context 
			final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final javax.ws.rs.core.Request req,
    		@PathParam("schema")
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths) throws Exception {
        		return request.request(Naming.QUERY, context, headers, uriInfo, req, null);
        	}
}