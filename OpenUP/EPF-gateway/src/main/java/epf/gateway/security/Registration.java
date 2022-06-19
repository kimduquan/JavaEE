package epf.gateway.security;

import java.io.InputStream;
import java.util.concurrent.CompletionStage;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.naming.Naming;

/**
 *
 * @author FOXCONN
 */
@Path(Naming.SECURITY)
@ApplicationScoped
@RolesAllowed(Naming.EPF)
public class Registration {
    
    /**
     * 
     */
    @Inject
    transient Application request;
    
    /**
     * @param context
     * @param headers
     * @param uriInfo
     * @param req
     * @param body
     * @return
     * @throws Exception
     */
    @Path(Naming.Security.PRINCIPAL)
    @POST
    public CompletionStage<Response> createPrincipal(
    		@Context final SecurityContext context,
            @Context final HttpHeaders headers, 
            @Context final UriInfo uriInfo,
            @Context final javax.ws.rs.core.Request req,
            final InputStream body) throws Exception {
    	return request.request(Naming.SECURITY, context, headers, uriInfo, req, body);
    }
}
