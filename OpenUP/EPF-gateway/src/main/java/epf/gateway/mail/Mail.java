package epf.gateway.mail;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.naming.Naming;

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
    
    @POST
	@Consumes(MediaType.APPLICATION_JSON)
    public CompletionStage<Response> send(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
        return request.request(Naming.MAIL, context, headers, uriInfo, req, body);
    }
}
