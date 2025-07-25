package epf.gateway.file;

import java.io.InputStream;
import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;

@Path(Naming.FILE)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Files {
    
    @Inject
    transient Application request;
    
    @Inject
    transient JsonWebToken jwt;
    
    @POST
	@Path("{paths: .+}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
    @RunOnVirtualThread
    public Response createFile(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            @PathParam("paths")
    		final List<PathSegment> paths,
            final InputStream body
    ) throws Exception {
    	return request.buildRequest(Naming.FILE, jwt, headers, uriInfo, req, body);
    }
    
    @GET
    @Path("{paths: .+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @RunOnVirtualThread
    public Response lines(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            @PathParam("paths")
    		final List<PathSegment> paths
    ) throws Exception {
    	return request.buildRequest(Naming.FILE, jwt, headers, uriInfo, req, null);
    }
    
    @DELETE
    @Path("{paths: .+}")
    @RunOnVirtualThread
    public Response delete(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            @PathParam("paths")
    		final List<PathSegment> paths
    ) throws Exception {
    	return request.buildRequest(Naming.FILE, jwt, headers, uriInfo, req, null);
    }
}
