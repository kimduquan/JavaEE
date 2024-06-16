package epf.gateway.query;

import java.io.InputStream;
import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;

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
     * 
     */
    @Inject
    transient JsonWebToken jwt;
	
	/**
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @param schema
	 * @param entity
	 * @param entityId
	 */
	@GET
    @Path(Naming.Query.Client.ENTITY_PATH)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response getEntity(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            @PathParam(Naming.Query.Client.SCHEMA) final String schema,
            @PathParam(Naming.Query.Client.ENTITY) final String entity,
            @PathParam(Naming.Query.Client.ID) final String entityId) throws Exception {
    	return request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null);
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
    @RunOnVirtualThread
	public Response countEntity(
			@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            @PathParam(Naming.Query.Client.SCHEMA) final String schema,
            @PathParam(Naming.Query.Client.ENTITY) final String entity
            ) throws Exception {
    	return request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null);
    }
	
	/**
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@PATCH
    @Path(Naming.Query.Client.ENTITY)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
	public Response fetchEntities(
			@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            final InputStream body) throws Exception {
    	return request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, body);
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
    @RunOnVirtualThread
	public Response executeQuery(
    		@Context 
    		final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final Request req,
    		@PathParam(Naming.Query.Client.SCHEMA)
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths) throws Exception {
    	return request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null);
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
    @RunOnVirtualThread
	public Response executeCountQuery(
			@Context 
			final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final Request req,
    		@PathParam(Naming.Query.Client.SCHEMA)
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths) throws Exception {
		return request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null);
    	}
	
	/**
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@GET
	@Path(Naming.Query.SEARCH)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response search(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req) throws Exception {
		return request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null);
    }
	
	/**
	 * @param context
	 * @param headers
	 * @param uriInfo
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@HEAD
	@Path(Naming.Query.SEARCH)
    @RunOnVirtualThread
	public Response count(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req) throws Exception {
		return request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null);
    }
}
