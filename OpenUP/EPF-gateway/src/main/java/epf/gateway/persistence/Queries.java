package epf.gateway.persistence;

import java.util.List;
import java.util.concurrent.CompletionStage;
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
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.naming.Naming;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@Path(Naming.PERSISTENCE)
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Queries {
    
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
     * @param paths
     */
    @GET
    @Path("{schema}/{criteria: .+}")
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> executeQuery(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            @PathParam("schema") final String schema,
            @PathParam("criteria")
            final List<PathSegment> paths
            ) throws Exception {
        return request.request(context, headers, uriInfo, req, null);
    }
    
    /**
     * @param headers
     * @param uriInfo
     * @param req
     * @return
     * @throws Exception 
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> search(
    		@Context final SecurityContext context,
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req
            ) throws Exception {
    	return request.request(context, headers, uriInfo, req, null);
    }
}
