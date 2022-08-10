package epf.gateway.query;

import java.io.InputStream;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.PATCH;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.gateway.internal.ResponseUtil;
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
    @Path("entity/{schema}/{entity}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
    public Response getEntity(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId) throws Exception {
		return ResponseUtil.buildResponse(request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null).invoke(), uriInfo.getBaseUri());
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
	public Response countEntity(
			@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity
            ) throws Exception {
		return ResponseUtil.buildResponse(request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null).invoke(), uriInfo.getBaseUri());
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
    @Path("entity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response fetchEntities(
			@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            final InputStream body) throws Exception {
		return ResponseUtil.buildResponse(request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, body).invoke(), uriInfo.getBaseUri());
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
	public Response executeQuery(
    		@Context 
    		final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final Request req,
    		@PathParam("schema")
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths) throws Exception { 
			return ResponseUtil.buildResponse(request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null).invoke(), uriInfo.getBaseUri());
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
	public Response executeCountQuery(
			@Context 
			final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final Request req,
    		@PathParam("schema")
            final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths) throws Exception {
		return ResponseUtil.buildResponse(request.buildRequest(Naming.QUERY, jwt, headers, uriInfo, req, null).invoke(), uriInfo.getBaseUri());
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
	@Path(Naming.SECURITY)
	@Produces(MediaType.APPLICATION_JSON)
    public Response getToken(
    		@Context 
    		final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final javax.ws.rs.core.Request req) throws Exception {
		return ResponseUtil.buildResponse(request.buildRequest(Naming.CACHE, null, headers, uriInfo, req, null).invoke(), uriInfo.getBaseUri());
    }
}
