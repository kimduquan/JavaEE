package epf.gateway.management;

import java.io.InputStream;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Request;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path(Naming.MANAGEMENT)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Management {

	@Inject
    transient Application request;

    @POST
    @Path(Naming.Management.SESSION)
    @Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response newSession(
    		@Context final JsonWebToken jwt,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final Request req,
            final InputStream body) throws Exception {
    	return request.buildRequest(Naming.MANAGEMENT, jwt, headers, uriInfo, req, body);
    }
}
