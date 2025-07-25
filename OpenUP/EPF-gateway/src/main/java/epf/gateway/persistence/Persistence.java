package epf.gateway.persistence;

import java.io.InputStream;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;

@Path(Naming.PERSISTENCE)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Persistence {
    
    @Inject
    transient Application request;
    
    @Inject
    transient JsonWebToken jwt;
    
    @POST
    @Path("{schema}/{entity}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response persist(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            final InputStream body) throws Exception {
    	return request.buildRequest(Naming.PERSISTENCE, jwt, headers, uriInfo, req, body);
    }
    
    @PUT
    @Path("{schema}/{entity}/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response merge(
    		@Context final SecurityContext context,
    		@Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId,
            final InputStream body
            ) throws Exception {
    	return request.buildRequest(Naming.PERSISTENCE, jwt, headers, uriInfo, req, body);
    }
    
    @DELETE
    @Path("{schema}/{entity}/{id}")
    @RunOnVirtualThread
    public Response remove(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            @PathParam("schema") final String schema,
            @PathParam("entity") final String entity,
            @PathParam("id") final String entityId) throws Exception {
    	return request.buildRequest(Naming.PERSISTENCE, jwt, headers, uriInfo, req, null);
    }
}
