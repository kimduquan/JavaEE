package epf.gateway.schema;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;

@Path(Naming.SCHEMA)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Schema {
    
    @Inject
    transient Application request;
    
    @Inject
    transient JsonWebToken jwt;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response getEntities(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req) throws Exception {
		return request.buildRequest(Naming.SCHEMA, jwt, headers, uriInfo, req, null);
    }
    
    @GET
    @Path("embeddable")
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response getEmbeddables(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req) throws Exception {
		return request.buildRequest(Naming.SCHEMA, jwt, headers, uriInfo, req, null);
    }
}
