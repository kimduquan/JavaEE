package epf.gateway.mail;

import java.io.InputStream;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.gateway.Application;
import epf.naming.Naming;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.common.annotation.RunOnVirtualThread;

/**
 * 
 */
@Path(Naming.MAIL)
@ApplicationScoped
@RolesAllowed({Naming.Security.DEFAULT_ROLE, Naming.EPF})
public class Mail {

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
     * @param context
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception
     */
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    @Blocking
    public Response send(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final jakarta.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	return request.buildRequest(Naming.MAIL, jwt, headers, uriInfo, req, body);
    }
}
