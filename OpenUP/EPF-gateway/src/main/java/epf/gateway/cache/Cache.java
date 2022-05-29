package epf.gateway.cache;

import java.util.concurrent.CompletionStage;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import epf.gateway.Application;
import epf.naming.Naming;

/**
 * @author PC
 *
 */
@Path(Naming.CACHE)
@ApplicationScoped
@RolesAllowed(Naming.Security.DEFAULT_ROLE)
public class Cache {

	/**
	 * 
	 */
	@Inject
    transient Application request;
	
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
    public CompletionStage<Response> getToken(
    		@Context 
    		final SecurityContext context,
            @Context 
            final HttpHeaders headers, 
            @Context 
            final UriInfo uriInfo,
            @Context 
            final javax.ws.rs.core.Request req) throws Exception {
        return request.request(Naming.CACHE, context, headers, uriInfo, req, null);
    }
}
